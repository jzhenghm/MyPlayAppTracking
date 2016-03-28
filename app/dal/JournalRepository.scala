package dal

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import java.sql.Date
import play.api.Logger
import play.api.db.slick


import models.{Journal,CurrentDeploy}

import scala.concurrent.{ Future, ExecutionContext }

/**
 * A repository for Journal.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class JournalRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import driver.api._ 
  
  /**
   * Here we define the table. It will have a name of people
   */
  private class JournalTable(tag: Tag) extends Table[Journal](tag, "journal") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    
    def app = column[String]("app")
    
    def version = column[String]("version")
    
    /** The env column */
    def env = column[String]("env")

    /** The username column */
    def username = column[String]("username")
    
    def date = column[Date]("date");

    /**
     * This is the tables default "projection".
     *
     * It defines how the columns are converted to and from the Journal object.
     *
     * In this case, we are simply passing the id, name and page parameters to the Journal case classes
     * apply and unapply methods.
     */
   //(id: Long, env: String, app: String, version: String, username: String, date: Date)
    def * = (app, version, env, username, date) <> ((Journal.apply _).tupled, Journal.unapply)
  }

  /**
   * The starting point for all queries on the people table.
   */
  private val journal = TableQuery[JournalTable]
  private val currentDeploy = TableQuery[CurrentDeployTable]

  /**
   * Create a Journal in Json object.
   *
   * This is an asynchronous operation, it will return a future of the created Journal, which can be used to obtain the
   * id for that person.
   */

  def add(entry: Journal): Future[Journal] = db.run {
    (journal.map(p => (p.app, p.version, p.env, p.username, p.date))
      returning journal.map(_.id) 
      into ((allfields, id) => Journal(allfields._1, allfields._2, allfields._3, allfields._4, allfields._5))
    ) += (entry.app, entry.version, entry.env, entry.username, entry.date)
  }
  
  /**
   * Add new journal transaction
   */
  def addNewJournal(entry: Journal) = {
    val dbAction = (
      for {
        _ <- ((journal.map(p => (p.app, p.version, p.env, p.username, p.date))
          returning journal.map(_.id)
          into ((allfields, id) => Journal(allfields._1, allfields._2, allfields._3, allfields._4, allfields._5))) 
            += (entry.app, entry.version, entry.env, entry.username, entry.date))
        _ <- {
          val q = for { c <- currentDeploy if c.app === entry.app && c.env === entry.env } yield c.version
          q.update(entry.version)
        }
      } yield ()).transactionally
    db run dbAction
  }
  
  def updateCurrentDeploy(app:String, env:String, version:String) = db.run{
    Logger.info(s"update: app=$app version=$version env=$env")
    val newdeploy = CurrentDeploy(app, env, version)
     println(newdeploy)   
    val  q = for { c <- currentDeploy if c.app === app && c.env === env } yield c.version 
    q.update(version)
  }
   

  def currentDeploys: Future[Seq[CurrentDeploy]] = db.run {
    currentDeploy.result
  }
  
  /**
   * List all the people in the database.
   */
  def list(): Future[Seq[Journal]] = db.run {
    journal.result
  }

  /**
   * Search journal
   * If the {{{showOption}}} is true for all history else only search journal for current deployment
   */
  def search(app: Option[String], version: Option[String], env: Option[String], username: Option[String], date: Option[Date], showOption:String): Future[Seq[Journal]] = db.run {
    Logger.info(s"search for: app=$app version=$version env=$env username=$username date=$date")

    val rst0 = showOption match {
      case "A" => journal
      case _ => for {
        c <- currentDeploy
        j <- journal if (c.app === j.app && c.env === j.env && c.version === j.version)
      } yield j 
    }

    val ja = app match { case Some(_) => rst0.filter(_.app === app); case None => rst0}
    val jv = version match { case Some(_) => ja.filter(_.version === version); case None => ja }
    val je = env match { case Some(_) => jv.filter(_.env === env); case None => jv }
    val ju = username match { case Some(_) => je.filter(_.username === username); case None => je }
    val jd = date match { case Some(_) => ju.filter(_.date === date); case None => ju }
    
    val rst = jd.sortBy(_.date.desc)
    
    rst.result
  };
  
  private class CurrentDeployTable(tag: Tag) extends Table[CurrentDeploy](tag, "current_deploy") { 
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def env = column[String]("env")
    def app = column[String]("app")  
    def version = column[String]("version")
    
    def * = (env, app, version) <> ((CurrentDeploy.apply _).tupled, CurrentDeploy.unapply)
  }
   
}