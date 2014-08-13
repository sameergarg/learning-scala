package controllers

import models.Greeting
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.GreetingService

class GreeterController(greetingService: GreetingService) extends Controller {

  val greetings = Seq(Greeting(1, greetingService.greetingMessage("en"), "sameer"), Greeting(2, greetingService.greetingMessage("it"), "sam"))

  def greet = Action {
    Ok(Json.toJson(greetings))
  }

}
