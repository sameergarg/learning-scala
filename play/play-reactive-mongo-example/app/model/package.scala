import play.api.libs.json.{Json, Format}

package object model {

  abstract class Document {
    def _id : String
  }

  case class Book(_id: String, name: String) extends Document


}
