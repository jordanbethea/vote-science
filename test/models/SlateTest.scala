package models

import framework.DatabaseTemplate
import org.scalatestplus.play._

import play.api.Application

class SlateTest extends PlaySpec with DatabaseTemplate {

  val insert1:Slate = new Slate(-20, "Slate 1", "Slate Maker")

  val insertResult:Slate = new Slate(1, "Slate 1", "Slate Maker")



  "Slate table schema" must {
    "create without error" in {
      val slates = injector.instanceOf[Slates]
      exec(slates.createSchema)
    }

    "insert a new slate" in {
      val slates = injector.instanceOf[Slates]
      //exec(slates.createSchema)

      exec(slates.add(insert1))

      val result:Seq[Slate] = exec(slates.listAll)
      result must contain (insertResult)
    }

    "get an existing slate by id" in {
      val slates = injector.instanceOf[Slates]

      exec(slates.add(insert1))

      val result:Slate = exec(slates.get(1)).getOrElse(null)
      result must be (insertResult)
    }
  }
}
