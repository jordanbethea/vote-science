package models.db

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slick.jdbc.H2Profile.api._
import com.google.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class FPTPChoice(ballotID: Long, questionID: Long, candidateID:Long)

class FPTPTableDef(tag: Tag) extends Table[FPTPChoice](tag, "FPTP_CHOICES") {
  def ballotID = column[Long]("BALLOT_ID")
  def questionID = column[Long]("QUESTION_ID")
  def candidateID = column[Long]("CANDIDATE_ID")

  override def * = (ballotID, questionID, candidateID).mapTo[FPTPChoice]
  def ballotKey = foreignKey("FPTP_BALLOT_FK", ballotID, BallotRepository.ballots)(_.id)
  def questionKey = foreignKey("FPTP_QUESTION_FK", questionID, QuestionRepository.questions)(_.id)
  def candidateKey = foreignKey("FPTP_CANDIDATE_FK", candidateID, CandidateRepository.candidates)(_.id)
}

object FPTPRepository {
  val fptpResults = TableQuery[FPTPTableDef]
}

class FPTPRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit executionContext: ExecutionContext)
                extends HasDatabaseConfigProvider[JdbcProfile]{

  def add(choice: FPTPChoice): Future[Int] = {
    dbConfig.db.run(FPTPRepository.fptpResults += choice)
  }

  def addAll(choices: Seq[FPTPChoice]): Future[Option[Int]] = {
    dbConfig.db.run(FPTPRepository.fptpResults ++= choices)
  }

  def getChoicesForBallot(ballotID: Long): Future[Seq[FPTPChoice]] = {
    dbConfig.db.run(FPTPRepository.fptpResults.filter(_.ballotID === ballotID).result)
  }
}
