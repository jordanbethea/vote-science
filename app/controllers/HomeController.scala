package controllers

import javax.inject._
import models.{Slate, Slates}
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Failure}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, val slates: Slates) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def slateList() = Action { implicit request: Request[AnyContent] =>
    import scala.concurrent.duration._

    val list = scala.concurrent.Await.result(slates.listAll, 2.seconds)
    Ok(views.html.slateList(list))
  }

  def addSlate() = Action { implicit request: Request[AnyContent] =>
    import scala.concurrent.duration._
    val newSlate:Slate = new Slate(0, "New Slate", "Test User")

    val resultF = scala.concurrent.Await.result(slates.add(newSlate), 2.seconds)
    Ok(resultF.toString)
  }
}
