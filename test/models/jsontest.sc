package models

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._




object jsontest {
import models.Journal
  val json1: JsValue = Json.parse("""
[{
  "app" : "pin code service",
  "env" : "qa",
  "username": "jack",
    "version" : "0.2.4",
    "date" : 1454043600000
},{
  "app" : "web service",
  "env" : "prd",
  "username": "john",
    "version" : "0.2.4",
    "date" : 1454043600000
}]
""")                                              //> json1  : play.api.libs.json.JsValue = [{"app":"pin code service","env":"qa",
                                                  //| "username":"jack","version":"0.2.4","date":1454043600000},{"app":"web servic
                                                  //| e","env":"prd","username":"john","version":"0.2.4","date":1454043600000}]

// implicit val journalFormat = Json.format[Journal]
  implicit val journalReads: Reads[Journal]  =  (
        (JsPath \ "app").read[String] and
        (JsPath \ "env").read[String] and
        (JsPath \ "username").read[String] and
        (JsPath \ "version").read[String] and
        (JsPath \ "date").read[java.sql.Date])(Journal.apply _)
                                                  //> journalReads  : play.api.libs.json.Reads[models.Journal] = play.api.libs.jso
                                                  //| n.Reads$$anon$8@42e99e4a

val jobj = Json.obj("journals" -> json1)          //> jobj  : play.api.libs.json.JsObject = {"journals":[{"app":"pin code service"
                                                  //| ,"env":"qa","username":"jack","version":"0.2.4","date":1454043600000},{"app"
                                                  //| :"web service","env":"prd","username":"john","version":"0.2.4","date":145404
                                                  //| 3600000}]}

(jobj \ "journals").as[Seq[Journal]]              //> java.lang.NoClassDefFoundError: models/Journal
                                                  //| 	at models.jsontest$$anonfun$main$1$$anonfun$1.apply(models.jsontest.scal
                                                  //| a:34)
                                                  //| 	at models.jsontest$$anonfun$main$1$$anonfun$1.apply(models.jsontest.scal
                                                  //| a:34)
                                                  //| 	at play.api.libs.functional.FunctionalBuilder$CanBuild5$$anonfun$apply$1
                                                  //| 3.apply(Products.scala:134)
                                                  //| 	at play.api.libs.functional.FunctionalBuilder$CanBuild5$$anonfun$apply$1
                                                  //| 3.apply(Products.scala:134)
                                                  //| 	at play.api.libs.json.JsResult$class.map(JsResult.scala:77)
                                                  //| 	at play.api.libs.json.JsSuccess.map(JsResult.scala:9)
                                                  //| 	at play.api.libs.json.Reads$$anonfun$map$1.apply(Reads.scala:45)
                                                  //| 	at play.api.libs.json.Reads$$anonfun$map$1.apply(Reads.scala:45)
                                                  //| 	at play.api.libs.json.Reads$$anon$8.reads(Reads.scala:122)
                                                  //| 	at play.api.libs.json.Json$.fromJson(Json.scala:125)
                                                  //| 	at play.api.libs.json.LowPriorityDefaultReads$$anon$2$$anonfun$reads$1.a
                                                  //| pply(Reads.scala:165)
                                                  //| 	at play.api.libs.json.LowPriorityDefaultReads$$anon$2$$ano
                                                  //| Output exceeds cutoff limit.
 
}