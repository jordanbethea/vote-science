package framework

import play.api.inject.guice.GuiceApplicationBuilder
import play.api.inject.bind
import models._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import slick.jdbc.JdbcProfile
//import play.api.db.Database
//import play.api.db.Databases
import slick.dbio.DBIO

trait DatabaseTemplate {

  lazy val inMemoryDataBaseConf: Map[String, Any] = Map[String, Any](
    "slick.dbs.default.profile" -> "slick.jdbc.H2Profile$",
    "slick.dbs.default.db.driver" -> "org.h2.Driver",
    "slick.dbs.default.db.url" -> "jdbc:h2:mem:play"
  )

  val builder = new GuiceApplicationBuilder()
    .configure(inMemoryDataBaseConf)

  val application = builder.build()

  def exec[T](future: Future[T]): T =
    Await.result(future , 2.seconds)


  //pattern for testing with non-slick databases
  /* def withTestDatabase[T](block: Database => T) = {
    Databases.withInMemory(
     name = "testDatabase",
      urlOptions = Map(
        "MODE" -> "H2"
      ),
      config = Map(
        "logStatements" -> true
      )
    )(block)
  } */
}