package controllers

import play.api.Logger
import play.api.mvc.Controller

class TryOuts extends Controller {
    def submit = LoggingAction { 
    Ok("Hello World")
  }
}