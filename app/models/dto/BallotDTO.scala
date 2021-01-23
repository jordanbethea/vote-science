package models.dto

import play.api.libs.json._

case class BallotDTO (id: Option[Long], voter: String, slateID: Long)

object BallotDTO {
  implicit val ballotWrites: Writes[BallotDTO] = Json.writes[BallotDTO]
  implicit val ballotReads: Reads[BallotDTO] = Json.reads[BallotDTO]

  //implicit val ballotSeqWrites: Writes[Seq[BallotDTO]] = Json.writes[Seq[BallotDTO]]
  //implicit val ballotSeqReads: Reads[Seq[BallotDTO]] = Json.reads[Seq[BallotDTO]]
}

case class FPTPModelDTO (choices: Seq[FPTPChoiceDTO])

object FPTPModelDTO {
  implicit val fptpModelWrites: Writes[FPTPModelDTO] = Json.writes[FPTPModelDTO]
  implicit val fptpModelReads: Reads[FPTPModelDTO] = Json.reads[FPTPModelDTO]
}

case class FPTPChoiceDTO (ballotID: Long, questionID: Long, candidateID: Long)

object FPTPChoiceDTO {
  implicit val fptpChoiceWrites: Writes[FPTPChoiceDTO] = Json.writes[FPTPChoiceDTO]
  implicit val fptpChoiceReads: Reads[FPTPChoiceDTO] = Json.reads[FPTPChoiceDTO]
}