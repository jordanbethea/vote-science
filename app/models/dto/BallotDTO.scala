package models.dto

import play.api.libs.json._

case class BallotDTO (id: Option[Long], voter: String, slateID: Long)

object BallotDTO {
  implicit val ballotWrites: Writes[BallotDTO] = Json.writes[BallotDTO]
}

case class FPTPChoiceDTO (ballotID: Long, questionID: Long, candidateID: Long)

object FPTPChoiceDTO {
  implicit val fptpChoiceWrites: Writes[FPTPChoiceDTO] = Json.writes[FPTPChoiceDTO]
}