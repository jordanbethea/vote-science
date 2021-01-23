package controllers

import javax.inject.{Inject, Singleton}
import models.db.{BallotRepository, SlateRepository}
import models.dto.{BallotDTO, FPTPModelDTO}
import play.api.mvc.{AbstractController, ControllerComponents}
import play.api.libs.json._
import play.api.libs.json.{JsResult, Json}

import scala.concurrent.ExecutionContext

@Singleton
class VotingController @Inject()(slatesRepo: SlateRepository, ballotRepo: BallotRepository, cc: ControllerComponents)
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

  def saveBallot(ballotID: Long) = Action.async(parse.json) { request =>
    Console.println(s"Running save ballot, request body: ${request.body}")
    //top level ballot info
    val ballotInfo = (request.body \ "details").get
    val ballotJson: JsResult[BallotDTO] = Json.fromJson[BallotDTO](ballotInfo)
    val ballot: BallotDTO = ballotJson match {
      case JsSuccess(value: BallotDTO, path: JsPath) => value
      case e @ JsError(_) =>
        Console.println(s"Parse Errors: ${JsError.toJson(e).toString()}")
        null
    }

    //First Past the post model data
    val fptpjson = request.body \ "fptpModel"
    val fptpResult = if(fptpjson.isDefined) {
      Json.fromJson[FPTPModelDTO](fptpjson.get).asOpt;
    } else None

    //save all data, return result
    for( ballotResult <- ballotRepo.addBallotAndModelData(ballot, fptpResult))
      yield {
        Ok(Json.toJson(ballotResult))
      }
  }

//  def getSlateResults(slateID: Long) = Action.async {
//    for {
//      voteResults <- ballotRepo.getBallotsForSlate(slateID)
//    } yield {
//      val dto: Seq[BallotDTO] = voteResults.map(ballot => BallotDTO(Option(ballot.id), ballot.voter, ballot.slateID))
//      //Ok(dto)
//    }
//  }

}
