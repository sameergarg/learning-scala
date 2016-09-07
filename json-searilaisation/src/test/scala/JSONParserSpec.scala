import org.specs2.matcher.MatcherMacros
import org.specs2.mutable.Specification
import scala.language.experimental.macros
/**
  * Created by samegarg on 07/09/2016.
  */
class JSONParserSpec extends Specification with MatcherMacros {
  override def is = s2"""

 This is a specification to check the Json parsing

 The 'Json Parser' string should
   parse json string to object                              $e1
   parse object to json string                              $e2
                                                                 """

  def e1 = {
    val json: List[Person] = JSONParser.fromJson(JSONParser.personsJson)
    json must have size (1)
    json.head must matchA[Person].name("Joe")
    json.head.jobs.head must beEqualTo("plumber")
    json.head.kids.size must beEqualTo(0)
    json.head.kids must be empty
  }
  def e2 = {
    val json: String = JSONParser.toJson(JSONParser.persons)
    json must contain("Joe")
    json must contain("\"kids\":[]")
    json must contain("\"jobs\":[]")
  }


}
