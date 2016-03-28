package controllers

import dal.JournalRepository

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.test.{ PlaySpecification, WithServer }

import java.sql.Date
import java.io.ByteArrayOutputStream

class JournalControllerIntegrationSpec extends PlaySpecification {
  import models.Journal

  implicit val journalReads: Reads[Journal] = (
    (JsPath \ "app").read[String] and
    (JsPath \ "env").read[String] and
    (JsPath \ "username").read[String] and
    (JsPath \ "version").read[String] and
    (JsPath \ "date").read[Date])(Journal.apply _)

  val Localhost = "http://localhost:"

  "JournalController" should {
    "fetch all journals" in new WithServer {

      val response = await(WS.url(Localhost + port + "/journals").get())
      response.status must equalTo(OK)

      val jobj = Json.obj("journals" -> response.json)

      val journals = (jobj \ "journals").as[Seq[Journal]]
      journals.size must equalTo(23)
    }

    "fetch all current deployments" in new WithServer {

      import models.CurrentDeploy

      val response = await(WS.url(Localhost + port + "/deploys").get())
      response.status must equalTo(OK)

      val jobj = Json.obj("currentDeploys" -> response.json)

      val journals = (jobj \ "currentDeploys").as[Seq[CurrentDeploy]]
      journals.size must equalTo(9)
    }

  }
  
   
//    def searchHelper(param: String)(implicit port: Int) = {
//    import play.api.Play.current
//    val response = await(WS.url(Localhost + port + s"/journals/$param").get())
// //     withQueryString(params: _*).get())
//    response.status must equalTo(OK)
//    
//    
//     val jobj = Json.obj("journals" -> response.json)
//
//    (jobj \ "Journal").as[Seq[Journal]]
//    
//
//  }
//    
//    "fetch journals in a given environment" in new WithServer {
//       val journals = searchHelper("prod")
//    journals.size must equalTo(2)
//    } 
//    
//    
// def searchHelper(params: (String, String)*)(implicit port: Int) = {
//    import play.api.Play.current
//    
//   
//    
//    val response = await(WS.url(Localhost + port + "/journal/search").
//      withQueryString(params: _*).post(bytes)(Writeable.wBytes).get())
//    response.status must equalTo(OK)
//    
//    val jobj = Json.obj("journals" -> response.json)
//
//    (jobj \ "Journal").as[Seq[Journal]]
//  }
// 
//   "Return search results when given an author" in new WithServer {
//    val books = searchHelper(("env", "prod"))
//    books.size must equalTo(11)
//  }
}