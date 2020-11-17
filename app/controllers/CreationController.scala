package controllers

import javax.inject.Singleton
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import play.api.libs.json.{JsResult, Json}
import models.dto.SlateDTO
import models.db._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


@Singleton
class CreationController @Inject()(slatesRepo: SlateRepository,
                                   questionRepo: QuestionRepository,
                                   candidateRepo: CandidateRepository,
                                   cc: ControllerComponents)
                                  (implicit ex: ExecutionContext) extends AbstractController(cc) {

  def createSlate = Action.async(parse.json) { request =>
    val newSlateParsing: JsResult[SlateDTO] = Json.fromJson[SlateDTO](request.body);
    val newSlate:SlateDTO = newSlateParsing match {
      case JsSuccess(value: SlateDTO, path: JsPath) => value
      case e @ JsError(_) =>
        Console.println(s"Parse errors: ${JsError.toJson(e).toString()} ")
        null
    }

    val questions = newSlate.questions
    val candidates = questions.flatMap(question => question.candidates)

    Console.println(s"Request: ${request.toString()}");
    Console.println(s"params?: ${request.body}");
    Console.println(s"parsed slate: ${(if(newSlate == null) "it's null" else newSlate.toString())}")

    slatesRepo.fullAdd(newSlate).map { result =>
      Ok(Json.obj("content" -> s"Successfully created Slate: $result"))
    }
  }

  def getSlates = Action.async {
    val allSlatesF = slatesRepo.listAll
    val allQuestionsF = questionRepo.listAll
    val allCandidatesF = candidateRepo.listAll

    for {
      slates <- allSlatesF
      questions <- allQuestionsF
      candidates <- allCandidatesF
    } yield {
      Console.println("Running actual get slates: :")
      Console.println(Json.toJson(SlateRepository.constructSlateDTO(slates, questions, candidates)))
      Ok(Json.toJson(SlateRepository.constructSlateDTO(slates, questions, candidates)))
    }
  }

  def getSlatesTest = Action.async {
//    val testSlate = new SlateDTO(Option(1l), "test slate", "creator", Nil)
//    val testSlates = Array(testSlate)
    slatesRepo.listAll.map { testSlatesDB =>
      val testSlates = SlateRepository.constructSlateDTO(testSlatesDB, Nil, Nil)
      val testSlatesJson = Json.toJson(testSlates)
      val testSlatesString = Json.stringify(testSlatesJson)
      Console.println(s"Returning: $testSlatesString")

      Ok(testSlatesJson)
}
  }
}
