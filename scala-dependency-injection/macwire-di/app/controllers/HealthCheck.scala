package controllers

import play.api.mvc.{Action, Controller}

object HealthCheck extends Controller {

  def ping = Action {
    Ok("pong")
  }

  def version = Action {
    val version = "0.1"
    Ok(version)
  }

}
