package models

import framework.DatabaseTemplate
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._

class CandidateTest extends PlaySpec with DatabaseTemplate with BeforeAndAfterEach {
  val slates = application.injector.instanceOf[Slates]
  val questions = application.injector.instanceOf[Questions]
  val candidates = application.injector.instanceOf[Candidates]

  override def beforeEach() = {
    exec(slates.createSchema)
    exec(questions.createSchema)
    exec(candidates.createSchema)
  }

  override def afterEach() = {
    exec(candidates.dropSchema)
    exec(questions.dropSchema)
    exec(slates.dropSchema)
  }

  val slate1 = new Slate(-1, "Slate 1", "Slate Maker")
  val question1 = new Question(-1, 1, "Pick a Candidate:")
  val candidate1 = new Candidate(-1, "Benjamin Franklin", "kite guy", 1)

  "Candidates" must {
    "create without error" in {}

    "require a question to insert a new candidate" in {
      an[JdbcSQLIntegrityConstraintViolationException] must be thrownBy (exec(candidates.add(candidate1)))
    }

    "create a candidate for a question" in {
      exec(slates.add(slate1))
      exec(questions.add(question1))

      val result = exec(candidates.add(candidate1))

      result must be (1)

      val resultList = exec(candidates.listAll)
      resultList.length must be (1)
    }

    "get existing candidate" in {
      exec(slates.add(slate1))
      exec(questions.add(question1))
      exec(candidates.add(candidate1))

      val result = exec(candidates.get(1)).getOrElse(null)
      result must not be (null)
      result.name must be ("Benjamin Franklin")
    }

    "delete existing candidate" in {
      exec(slates.add(slate1))
      exec(questions.add(question1))
      exec(candidates.add(candidate1))

      val result = exec(candidates.delete(1))
      result must be (1)

      val list = exec(candidates.listAll)
      list.length must be (0)
    }

  }
}
