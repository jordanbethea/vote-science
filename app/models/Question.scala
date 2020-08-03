package models

import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import scala.concurrent.{ExecutionContext, Future}



case class Question (id: Long, slateID: Long, text:String)

class QuestionTableDef(tag: Tag) extends Table[Question](tag, "questions") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def slateID = column[Long]("slate_id")
  def text = column[String]("text")

  override def * = (id, slateID, text).mapTo[Question]
  def slate = foreignKey("SLATE_QUESTION_FK", slateID, Slates.slates)(_.id)
}

object Questions {
  val questions = TableQuery[QuestionTableDef]
}

class Questions @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                          (implicit executionContext: ExecutionContext)
                          extends HasDatabaseConfigProvider[JdbcProfile] {

  def listAll: Future[Seq[Question]] = {
    dbConfig.db.run(Questions.questions.result)
  }

  def get(id: Long): Future[Option[Question]] = {
    dbConfig.db.run(Questions.questions.filter(_.id === id).result.headOption)
  }

  def add(question: Question): Future[String] = {
    dbConfig.db.run(Questions.questions += question).map(res => "Slate successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    dbConfig.db.run(Questions.questions.filter(_.id === id).delete)
  }
}
