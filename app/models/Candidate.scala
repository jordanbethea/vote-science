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

  override def * = (id, name, description, questionID) <> (Candidate.tupled, Candidate.unapply)
}

class Candidates @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  val candidates = TableQuery[CandidateTableDef]

  def listAll: Future[Seq[Candidate]] = {
    dbConfig.db.run(candidates.result)
  }

  def get(id: Long): Future[Option[Candidate]] = {
    dbConfig.db.run(candidates.filter(_.id == id).result.headOption)
  }

  def add(candidate: Candidate): Future[String] = {
    dbConfig.db.run(candidates += candidate).map(res => "Slate successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(candidates.filter(_.id === id).delete)
  }
}