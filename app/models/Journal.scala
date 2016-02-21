package models

import play.api.libs.json._
import java.sql.Date

case class Journal(app: String, version: String, env: String,  username: String, date: Date)

object Journal {

  implicit val journalFormat = Json.format[Journal]
}

case class CurrentDeploy (env: String, app: String, version: String)

object CurrentDeploy {

  implicit val journalFormat = Json.format[CurrentDeploy]
}