import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/index';

@Injectable({
  providedIn: 'root'
})
export class VotingService {
  private saveBallotURL = '/api/vote/';

  constructor(private http: HttpClient) {
  }

  public saveBallot(ballot: Ballot): Observable<any> {
    let generatedURL: string = `${this.saveBallotURL}${ballot.details.slateID}`
    return this.http.post(generatedURL, ballot);
  }

  public getSlateResults(slateID: number): Observable<Array<Ballot>> {
    let generatedURL: string = `api/results/${slateID}`;
    return this.http.get<Array<Ballot>>(generatedURL);
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
