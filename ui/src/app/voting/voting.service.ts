import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/index';

@Injectable({
  providedIn: 'root'
})
export class VotingService {
  private saveBallotURl = '/api/vote';

  constructor(private http: HttpClient) { }

  public saveBallot(ballot: Ballot): Observable<any> {
    let generatedURL: string = `${this.saveBallotURl}${ballot.details.slateID}`
    return this.http.post(generatedURL, ballot);
  }
}

export interface Ballot {
  details: BallotDetails
  fptpModel: FPTPModel | null
}

export interface BallotDetails {
  id: number | null
  voter: string
  slateID: number
}

export interface FPTPModel {
  choices : Array<FPTPChoice>
}

export interface FPTPChoice {
  ballotID: number
  questionID: number
  candidateID: number
}
