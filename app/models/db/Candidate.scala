package models.db

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import com.google.inject.Inject
import models.dto.CandidateDTO
import slick.jdbc.JdbcProfile
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContext, Future}

case class Candidate (id: Long, name: String, description: String, questionID:Long)

object Candidate {
  implicit def candidateDTOToCandidate(questionID: Long, candidateDTO: CandidateDTO): Candidate = {
    new Candidate(questionID, candidateDTO.name, candidateDTO.description, candidateDTO.id.getOrElse(0))
  }
  //https://github.com/VirtusLab/unicorn/issues/11
  val tupled = (this.apply _).tupled
}

class CandidateTableDef(tag: Tag) extends Table[Candidate](tag, "CANDIDATES") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def description = column[String]("DESCRIPTION")
  def questionID = column[Long]("QUESTION_ID")

  override def * = (id, name, description, questionID).mapTo[Candidate]
  def question = foreignKey("QUESTION_CANDIDATE_FK", questionID, QuestionRepository.questions)(_.id)
}

object CandidateRepository {
  val candidates = TableQuery[CandidateTableDef]
}

class CandidateRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                   (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  def createSchema = {
    dbConfig.db.run(CandidateRepository.candidates.schema.create)
  }

  def dropSchema = {
    dbConfig.db.run(CandidateRepository.candidates.schema.drop)
  }

  def listAll: Future[Seq[Candidate]] = {
    dbConfig.db.run(CandidateRepository.candidates.result)
  }

  def get(id: Long): Future[Option[Candidate]] = {
    dbConfig.db.run(CandidateRepository.candidates.filter(_.id === id).result.headOption)
  }

  def getForQuestions(ids: Seq[Long]): Future[Option[Candidate]] = {
    //For future reference - first version doesn't work because contains uses == and slick
    //requires ===, =!=
    //dbConfig.db.run(CandidateRepository.candidates.filter(c => ids.contains(c.questionID)).result.headOption)
    dbConfig.db.run(CandidateRepository.candidates.filter(_.questionID.inSet(ids)).result.headOption)
  }

  def add(candidate: Candidate): Future[Int] = {
    dbConfig.db.run(CandidateRepository.candidates += candidate)
  }

  def addAll(candidates: Seq[Candidate]) = {
    dbConfig.db.run(CandidateRepository.candidates ++= candidates)
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(CandidateRepository.candidates.filter(_.id === id).delete)
  }
}