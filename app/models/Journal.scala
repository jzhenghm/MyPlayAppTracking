package models

import play.api.libs.json._
//
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
//
import java.sql.Date

case class CurrentDeploy(env: String, app: String, version: String)

object CurrentDeploy {

  implicit val currentDeployFormat = Json.format[CurrentDeploy]

  implicit val currentDeployReads: Reads[CurrentDeploy] = (
    (JsPath \ "app").read[String] and
    (JsPath \ "env").read[String] and
    (JsPath \ "version").read[String])(CurrentDeploy.apply _)
}

case class Journal(app: String, version: String, env: String, username: String, date: Date)

object Journal {

  implicit val journalFormat = Json.format[Journal]
}