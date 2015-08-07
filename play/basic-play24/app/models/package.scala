import play.api.libs.json.{Json, Format}

/**
 *
 */
package object models {


  implicit val recipeFormat: Format[Recipe] = Json.format[Recipe]

  case class Recipe(name: String, ingredients: List[String])



}
