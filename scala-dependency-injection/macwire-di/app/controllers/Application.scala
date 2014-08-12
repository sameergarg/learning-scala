package controllers

import org.joda.time.LocalDateTime
import play.api._
import play.api.libs.json.{Format, Json}
import play.api.mvc._
import play.api.libs._



object Application extends Controller {

  case class Greeting(id: Int, message: String, name: String)

  object Greeting {
    implicit val GreetingFormat = Json.format[Greeting]
  }

  val greetings = Seq(Greeting(1, "hello", "sameer"), Greeting(2, "messi", "sam"))

  def index = Action {
    Ok(views.html.index("hello"))
  }

  def getGreetings = Action {
    Ok("hello")
  }


}