package controllers

import com.wordnik.swagger.annotations._
import models.Recipe
import play.api.libs.json.Json
import play.api.mvc.{Controller, Action}

/**
 *
 */
@Api(value = "/recipes", description = "Recipes operations")
object RecipeController extends Controller {

  val recipes = List(
    Recipe(name = "custard", ingredients = List("Milk", "Sugar", "Flavour")),
    Recipe(name = "cake", ingredients = List("Powder", "Sugar", "Flavour"))
  )

  @ApiOperation(
    value = "Searches the recipes",
    notes = "Search the recipes with ingredients matching the query parameter `keyword`",
    nickname = "recipe",
    httpMethod = "GET")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "keyword", value = "ingredient item", required = false,
      dataType = "application/json", paramType = "queryParam")))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "Matching search results")))
  def getRecipes(keyword: Option[String]) = Action {
    val matchingRecipes: List[Recipe] = keyword.collect {
      case word:String => recipes.filter(_.ingredients.mkString.contains(word))
    }.getOrElse(recipes)

    Ok(Json.toJson(matchingRecipes))
  }

}
