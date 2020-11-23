package models.db

import framework.DatabaseTemplate
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.scalatestplus.play._

class CandidateTest extends PlaySpec with DatabaseTemplate {
  val slates = application.injector.instanceOf[SlateRepository]
  val questions = application.injector.instanceOf[QuestionRepository]
  val candidates = application.injector.instanceOf[CandidateRepository]

  val slate1 = new Slate(-1, "Slate 1", "Slate Maker")
  val question1 = (slateID:Long) => new Question(-1, slateID, "Pick a Candidate:")
  val candidate1 = (questionID:Long) => new Candidate(-1, "Benjamin Franklin", "kite guy", questionID)

  "Candidates" must {
    "create without error" in {}

    "require a question to insert a new candidate" in {
      an[JdbcSQLIntegrityConstraintViolationException] must be thrownBy (exec(candidates.add(candidate1(30))))
    }

    "create a candidate for a question" in {
      val slateID = exec(slates.add(slate1))
      val questionID = exec(questions.add(question1(slateID)))

      val candidateID = exec(candidates.add(candidate1(questionID)))

      candidateID must not be (None)
    }

    "get existing candidate" in {
      val slateID = exec(slates.add(slate1))
      val questionID = exec(questions.add(question1(slateID)))
      val candidateID = exec(candidates.add(candidate1(questionID)))

      val result = exec(candidates.get(candidateID)).getOrElse(null)
      result must not be (null)
      result.name must be ("Benjamin Franklin")
    }

    "delete existing candidate" in {
      val slateID = exec(slates.add(slate1))
      val questionID = exec(questions.add(question1(slateID)))
      val candidateID = exec(candidates.add(candidate1(questionID)))

      val deleteResult = exec(candidates.delete(candidateID))
      deleteResult must be (1)

      val getResult = exec(candidates.get(candidateID))
      getResult must be (None)
    }

  }
}