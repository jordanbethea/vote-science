package models.db

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.H2Profile.api._
import com.google.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


case class Ballot(id: Long, slateID: Long, voter: String)

class BallotTableDef(tag: Tag) extends Table[Ballot](tag, "BALLOTS") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
  def slateID = column[Long]("SLATE_ID")
  def voter = column[String]("VOTER")

  override def * = (id, slateID, voter).mapTo[Ballot]
  def slate = foreignKey("BALLOT_SLATE_FK", slateID, SlateRepository.slates)(_.id)
}

object BallotRepository {
  val ballots = TableQuery[BallotTableDef]
  val ballotsInserts = ballots returning ballots.map(_.id)
}

class BallotRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                (implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  def add(ballot: Ballot) : Future[Long] = {
    dbConfig.db.run(BallotRepository.ballotsInserts += ballot)
  }

  def get(id: Long): Future[Option[Ballot]] = {
    dbConfig.db.run(BallotRepository.ballots.filter(_.id === id).result.headOption)
  }


}
