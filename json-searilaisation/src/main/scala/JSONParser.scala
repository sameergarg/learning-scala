import org.json4s.jackson.{Json, Serialization}
import org.json4s._
import org.json4s.native.JsonMethods._


case class Person(name: String, jobs: List[String], kids:List[String])

object JSONParser {

  val personsJson =
    """[
      |{
        |"name":"Joe",
        |"jobs":["plumber"]
      |}
      |]
      |""".stripMargin

  val persons = List(Person(name = "Joe", jobs = List.empty, kids = Nil))

  def fromJson(jsonString: String): List[Person] = {
    implicit val formats = DefaultFormats
    parse(personsJson).extract[List[Person]]
  }

  def toJson(persons: List[Person]) : String = {
    implicit val formats = Serialization.formats(NoTypeHints)
    import org.json4s.jackson.Serialization.{read, write}
    write(persons)
  }

}
