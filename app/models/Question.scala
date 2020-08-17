package models

import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import scala.concurrent.{ExecutionContext, Future}



case class Question (id: Long, slateID: Long, text:String)

class QuestionTableDef(tag: Tag) extends Table[Question](tag, "QUESTIONS") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def slateID = column[Long]("SLATE_ID")
  def text = column[String]("TEXT")

  override def * = (id, slateID, text).mapTo[Question]
  def slate = foreignKey("SLATE_QUESTION_FK", slateID, Slates.slates)(_.id)
}

object Questions {
  val questions = TableQuery[QuestionTableDef]
}

class Questions @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                          (implicit executionContext: ExecutionContext)
                          extends HasDatabaseConfigProvider[JdbcProfile] {

  def createSchema = {
    db.run(Questions.questions.schema.create)
  }

  def dropSchema = {
    db.run(Questions.questions.schema.drop)
  }

  def listAll: Future[Seq[Question]] = {
    dbConfig.db.run(Questions.questions.result)
  }

  def get(id: Long): Future[Option[Question]] = {
    dbConfig.db.run(Questions.questions.filter(_.id === id).result.headOption)
  }

  def add(question: Question): Future[Int] = {
    dbConfig.db.run(Questions.questions += question)
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(Questions.questions.filter(_.id === id).delete)
  }
}
