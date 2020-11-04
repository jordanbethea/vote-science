import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/index';


@Injectable({
  providedIn: 'root'
})
export class CreationService {
  private creationUrl = '/api/createSlate';

  constructor(private http: HttpClient) { }

  createNewSlate(newSlate: Slate) : Observable<any> {
    return this.http.post(this.creationUrl, newSlate);
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
