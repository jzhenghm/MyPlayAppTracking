package controllers

import java.text.SimpleDateFormat

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import dal.JournalRepository
import javax.inject.Inject
import javax.inject.Singleton
import models.Journal
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms.text
import play.api.i18n.I18nSupport
import play.api.i18n.MessagesApi
import play.api.libs.json.JsError
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.mvc.Action
import play.api.mvc.BodyParsers
import play.api.mvc.Controller
                                         
class JournalController @Inject() (repo: JournalRepository, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport{
  
  val  journalForm: Form[SearchJournalForm] = Form {
    mapping(
      "app" -> text,
      "version" ->  text,
      "env" -> text,
      "username" -> text,
      "date" -> text,
      "showOption"-> text
    )(SearchJournalForm.apply)(SearchJournalForm.unapply)
  }
  
   def index = Action {
   // repo.list().map { journal =>  Ok(views.html.index(journalForm)(journal))}
    Ok(views.html.index(journalForm))
  }
  
  /**
   * Returns a search result
   */
  def result(journals: Seq[Journal]) = Action {
    Ok(views.html.result(journalForm)(journals))
  }
  
  
  /**
   * a REST endpoint to add a new journal
   * {{{
   * curl -X POST -H "Content-Type: application/json" \
   * -d '{"app":"e-commerce web","version":"0.1.2","env":"prod","username":"mack","date":"2016-02-01"}' \
   * http://localhost:9000/journal/add
   * }}}
   * 
   */
  def addJournal = Action(parse.json) { request =>
    val b = request.body.validate[Journal]
    Logger(s"addJourna called $b")
    b.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      journal => {
        Logger.info(journal.toString())
        repo.addNewJournal(journal)
        Logger.info("saved")
        Ok(Json.obj("status" -> "OK"))
      }
    )
  }
  
  /**
   * A REST endpoint that search journal by a form
   */
  def searchJournal = Action.async { implicit request =>
    journalForm.bindFromRequest.fold(
      errorForm => {  Future.successful(Ok(views.html.index(errorForm))) },
      //      There were no errors in the from, so create the person.
      journal => {
        val date = journal.date match {
          case x if x.length > 0 =>
            val dataFormat = new SimpleDateFormat("yyyy-MM-dd")
            val date = dataFormat.parse(journal.date).getTime
            Option(new java.sql.Date(date))
          case _ => None

        }
        def strOption(str: String): Option[String] = str match {
          case x if x.length > 0 => Option(x)
          case _                 => None
        }

        val journals = repo.search(strOption(journal.app), strOption(journal.version),
          strOption(journal.env), strOption(journal.username),
          date, journal.showOption)

        journals.map { x => Ok(views.html.result(journalForm)(x)) }

      })

  } 
  
//  def searchJournalQurey = Action.async { implicit request =>
    
//  }
  
  /**
   * A REST endpoint that gets all the journal as JSON.
   */
  def getJournals = Action.async {
  	repo.list().map { journal =>  Ok(Json.toJson(journal)) }
  }
  
    def getCurrentDeploys = Action.async {
  	repo.currentDeploys.map { deploys =>  Ok(Json.toJson(deploys)) }
  }
    
  def  showEnv(env: String) = Action.async {
     val journals = repo.search(None,None, Some(env), None, None, "A")

        journals.map { x => Ok(views.html.result(journalForm)(x)) }
    
  }

}

case class SearchJournalForm(app: String, version: String, env: String, username: String, date: String, showOption: String)