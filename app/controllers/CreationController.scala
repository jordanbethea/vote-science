package controllers

import javax.inject.Singleton
import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents, Action}
import play.api.libs.json.Json


@Singleton
class CreationController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def createSlate = Action {
    Ok(Json.obj("content" -> "Received slate"))
  }
}
