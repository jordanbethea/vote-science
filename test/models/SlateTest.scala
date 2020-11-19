package models.db

import framework.DatabaseTemplate
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._

class SlateTest extends PlaySpec with DatabaseTemplate with BeforeAndAfterEach {

  val insert1:Slate = new Slate(-20, "Slate 1", "Slate Maker")
  val insert2:Slate = new Slate(-21, "Slate 2", "Slate Maker")
  val insert3:Slate = new Slate(-22, "Slate 3", "Other Slate Maker")
  val insert4:Slate = new Slate(-23, "Slate 4", "Other Slate Maker")

  val insertResult:Slate = new Slate(1, "Slate 1", "Slate Maker")

  val injector = application.injector
  val slates = injector.instanceOf[SlateRepository]
  exec(slates.dropSchema)

  override def beforeEach()= {
    //slates = injector.instanceOf[SlateRepository]
    exec(slates.createSchema)
  }

  override def afterEach()= {
    exec(slates.dropSchema)
    //slates.deleteAll
  }

  "Slate table schema" must {
    "create without error" in {
      exec(slates.listAll)
    }

    "insert a new slate" in {
      exec(slates.add(insert1))

      val result:Seq[Slate] = exec(slates.listAll)
      result must contain (insertResult)
    }

    "insert multiple slates" in {
      val inserts = Seq(insert1, insert2, insert3, insert4)

      exec(slates.addAll(inserts))

      val result:Seq[Slate] = exec(slates.listAll)
      result.length must be (4)
    }

    "get an existing slate by id" in {
      val inserts = Seq(insert1, insert2, insert3, insert4)

      exec(slates.addAll(inserts))

      val result:Slate = exec(slates.get(1)).getOrElse(null)
      result must be (insertResult)
    }

    "delete an existing slate" in {
      val inserts = Seq(insert1, insert2, insert3, insert4)

      exec(slates.addAll(inserts))

      slates.delete(1)

      val resultAll = exec(slates.listAll)
      resultAll.length must be (3)

      val resultSingle = exec(slates.get(1))
      resultSingle mustBe empty
    }
  }
}