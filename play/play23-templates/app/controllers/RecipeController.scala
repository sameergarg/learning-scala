package controllers

import models.Recipe
import play.api.libs.json.Json
import play.api.mvc.{Controller, Action}

/**
 *
 */
object RecipeController extends Controller {

  val recipes = List(
    Recipe(name = "custard", ingredients = List("Milk", "Sugar", "Flavour")),
    Recipe(name = "cake", ingredients = List("Powder", "Sugar", "Flavour"))
  )

  def getRecipes(keyword: Option[String]) = Action {
    val matchingRecipes: List[Recipe] = keyword.collect {
      case word:String => recipes.filter(_.ingredients.mkString.contains(word))
    }.getOrElse(recipes)

    Ok(Json.toJson(matchingRecipes))
  }

}
