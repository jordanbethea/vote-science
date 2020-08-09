package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import com.google.inject.Inject
import slick.jdbc.JdbcProfile
import slick.jdbc.H2Profile.api._
import scala.concurrent.{ExecutionContext, Future}

case class Slate(id: Long, title: String, creator: String)

class SlateTableDef(tag: Tag) extends Table[Slate](tag, "slates") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def creator = column[String]("creator")

  override def * = (id, title, creator).mapTo[Slate]
}

object Slates {
  val slates = TableQuery[SlateTableDef]
}

class Slates @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                       (implicit executionContext: ExecutionContext)
                        extends HasDatabaseConfigProvider[JdbcProfile] {

  def createSchema = {
    db.run(Slates.slates.schema.create)
  }

  def listAll: Future[Seq[Slate]] = {
    db.run(Slates.slates.result)
  }

  def get(id: Long): Future[Option[Slate]] = {
    db.run(Slates.slates.filter(_.id === id).result.headOption)
  }

  def add(slate: Slate): Future[String] = {
    System.out.println("db: "+db.toString())
    db.run(Slates.slates += slate).map(res => "Slate successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    db.run(Slates.slates.filter(_.id === id).delete)
  }
}

