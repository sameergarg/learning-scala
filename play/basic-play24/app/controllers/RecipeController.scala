package controllers

import com.wordnik.swagger.annotations.{ApiOperation, Api}
import models.Recipe
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
 *
 */
@Api(value = "/recipes", description = "Operations about recipes")
class RecipeController extends Controller {

  val recipes = List(
    Recipe(name = "custard", ingredients = List("Milk", "Sugar", "Flavour")),
    Recipe(name = "cake", ingredients = List("Powder", "Sugar", "Flavour"))
  )

  @ApiOperation(value = "Finds Pets by status",
    notes = "Multiple status values can be provided with comma seperated strings",
    response = classOf[Recipe],
    responseContainer = "List")
  def getRecipes(keyword: Option[String]) = Action {
    val matchingRecipes: List[Recipe] = keyword.collect {
      case word:String => recipes.filter(_.ingredients.mkString.contains(word))
    }.getOrElse(recipes)

    Ok(Json.toJson(matchingRecipes))
  }

}
