package models

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import com.google.inject.Inject
import slick.jdbc.JdbcProfile
import slick.jdbc.H2Profile.api._
import scala.concurrent.{ExecutionContext, Future}

case class Slate(id: Long, title: String, creator: String)

class SlateTableDef(tag: Tag) extends Table[Slate](tag, "SLATES") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def title = column[String]("TITLE")
  def creator = column[String]("CREATOR")

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

  def dropSchema = {
    db.run(Slates.slates.schema.drop)
  }

  def listAll: Future[Seq[Slate]] = {
    db.run(Slates.slates.result)
  }

  def get(id: Long): Future[Option[Slate]] = {
    db.run(Slates.slates.filter(_.id === id).result.headOption)
  }

  def add(slate: Slate): Future[Int] = {
    db.run(Slates.slates += slate)
  }

  def addAll(slates:Seq[Slate]): Future[Option[Int]] = {
    db.run(Slates.slates ++= slates)
  }

  def delete(id: Long): Future[Int] = {
    db.run(Slates.slates.filter(_.id === id).delete)
  }
}
