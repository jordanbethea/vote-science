package controllers

import javax.inject.{Inject, Singleton}
import models.db.SlateRepository
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json._

import scala.concurrent.ExecutionContext

@Singleton
class VotingController @Inject()(slatesRepo: SlateRepository, cc: ControllerComponents)
(implicit ex: ExecutionContext) extends AbstractController(cc){

  def loadBallot(ballotID: Long) = Action.async {
    for {
      slate <- slatesRepo.getFullSlate(ballotID)
    } yield {
      slate match {
        case Some(x) => Ok(Json.toJson(x))
        case None => NotFound
      }
    }
  }

}
