import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/index';


@Injectable({
  providedIn: 'root'
})
export class SlateService {
  private creationUrl = '/api/createSlate';
  private listUrl = '/api/slates';
  private loadSlateUrl = '/api/vote/';

  constructor(private http: HttpClient) { }

  createNewSlate(newSlate: Slate) : Observable<any> {
    return this.http.post(this.creationUrl, newSlate);
  }

  getAllSlates() : Observable<Array<Slate>> {
    return this.http.get<Array<Slate>>(this.listUrl);
  }

  loadSlate(slateID: number): Observable<Slate> {
    let generatedURL: string = `${this.loadSlateUrl}${slateID}`;
    return this.http.get<Slate>(generatedURL);
  }

}

export interface Slate {
  id: number | null
  title: string
  creator: string
  questions: Array<Question>
}

export interface Candidate {
  id: number | null
  name: string
  description: string
}

export interface Question {
  id: number | null
  text: string
  candidates: Array<Candidate>
}
