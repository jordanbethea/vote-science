package models.db

import framework.DatabaseTemplate
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._

class QuestionTest extends PlaySpec with DatabaseTemplate with BeforeAndAfterEach {

  val slate1 = new Slate(-1, "Slate 1", "Slate Maker")

  val question1 = new Question(-20, 1, "Pick a Candidate:")
  val question2 = new Question(-21, 2, "Pick another one:")

  val injector = application.injector
  val slates = injector.instanceOf[SlateRepository]
  val questions = injector.instanceOf[QuestionRepository]

//  override def beforeEach() = {
//    exec(slates.createSchema)
//    exec(questions.createSchema)
//  }
//
//  override def afterEach() = {
//    exec(questions.dropSchema)
//    exec(slates.dropSchema)
//  }

//  "Questions" must {
//    "create without error" in {
//    }
//
//    "require a slate to insert a new question" in {
//      an[JdbcSQLIntegrityConstraintViolationException] must be thrownBy (exec(questions.add(question1)))
//    }
//
//    "add a question to an existing slate" in {
//      exec(slates.add(slate1))
//
//      val result:Int = exec(questions.add(question1))
//
//      //result must equal ("Question successfully added")
//      result must equal (1)
//    }
//
//    "get existing question" in {
//      exec(slates.add(slate1))
//      exec(questions.add(question1))
//
//      val result = exec(questions.get(1)).getOrElse(null)
//      result.text must be ("Pick a Candidate:")
//    }
//
//    "delete existing question" in {
//      exec(slates.add(slate1))
//      exec(questions.add(question1))
//
//      val result = exec(questions.delete(1))
//      result must be (1)
//
//      val list = exec(questions.listAll)
//      list.length must be (0)
//    }
//
//  }
}