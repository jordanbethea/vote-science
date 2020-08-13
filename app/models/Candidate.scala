package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import com.google.inject.Inject
import slick.jdbc.JdbcProfile
import slick.jdbc.H2Profile.api._
import scala.concurrent.{ExecutionContext, Future}

case class Candidate (id: Long, name: String, description: String, questionID:Long)

class CandidateTableDef(tag: Tag) extends Table[Candidate](tag, "candidates") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def description = column[String]("description")
  def questionID = column[Long]("question_id")

  override def * = (id, name, description, questionID).mapTo[Candidate]
  def question = foreignKey("QUESTION_CANDIDATE_FK", questionID, Questions.questions)(_.id)
}

object Candidates {
  val candidates = TableQuery[CandidateTableDef]
}

class Candidates @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  def createSchema = {
    dbConfig.db.run(Candidates.candidates.schema.create)
  }

  def dropSchema = {
    dbConfig.db.run(Candidates.candidates.schema.drop)
  }

  def listAll: Future[Seq[Candidate]] = {
    dbConfig.db.run(Candidates.candidates.result)
  }

  def get(id: Long): Future[Option[Candidate]] = {
    dbConfig.db.run(Candidates.candidates.filter(_.id === id).result.headOption)
  }

  def add(candidate: Candidate): Future[Int] = {
    dbConfig.db.run(Candidates.candidates += candidate)
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(Candidates.candidates.filter(_.id === id).delete)
  }
}