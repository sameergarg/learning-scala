import play.api.libs.json.Json

package object models {

  case class Greeting(id: Int, message: String, name: String)

  object Greeting {
    implicit val GreetingFormat = Json.format[Greeting]
  }

}
