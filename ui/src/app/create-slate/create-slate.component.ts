import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormArray, FormControl, FormGroup } from '@angular/forms';
import { CreationService, Slate } from '../creation.service';


@Component({
  selector: 'app-create-slate',
  templateUrl: './create-slate.component.html',
  styleUrls: ['./create-slate.component.css']
})
export class CreateSlateComponent implements OnInit {

  constructor(private fb: FormBuilder, private createService: CreationService) { }

  form = this.fb.group({
    title: [''],
    creator: [''],
    questions: this.fb.array([
      this.fb.group({
        text: [''],
        candidates: this.fb.array([
          this.fb.group({
            name: [''],
            description: ['']
          })
        ])
      })
    ])
  });

  ngOnInit() {
  }

  getQuestions(): FormArray {
    return this.form.get('questions') as FormArray;
  }

  addQuestion() {
    this.getQuestions().push(
      this.fb.group({
        text: [''],
        candidates: this.fb.array([
          this.fb.group({
            name: [''],
            description: ['']
          })
        ])
      })
    );
  }

  /**
   * Returns list of candidates for a given question
   * @param question number representing the position of the question in the form array
   */
  getCandidates(questionPos: number): FormArray {
    const question = this.getQuestions().at(questionPos);
    return question.get('candidates') as FormArray;
  }

  addCandidate(questionPos: number) {
    this.getCandidates(questionPos).push(
      this.fb.group({
        name: [''],
        description: ['']
      })
    )
  }

  save() {
    const toSave: Slate = this.createSlateFromForm();
    this.createService.createNewSlate(toSave).subscribe(result => {
      console.log(JSON.stringify(result));
    });
  }

  /**
   * Pulls data from slate form to create Slate object to be passed to backend.
   */
  createSlateFromForm(): Slate {
    return {
        id: null,  //to start with, IDs are null until saved. Later update this to account for editing existing slates
        title: this.form.get('title')?.value ?? "",  //if title exists get value - if this is null, use blank string instead. optional chaining/nullish coalescing
        creator: this.form.get('creator')?.value ?? "",
        questions: (this.form.get('questions') as FormArray).controls.map(questionForm => {
          return {
            id: null,
            text: questionForm.get('text')?.value ?? "",
            candidates: (questionForm.get('candidates') as FormArray).controls.map(candidateForm => {
              return {
                id: null,
                name: candidateForm.get('name')?.value ?? "",
                description: candidateForm.get('description')?.value ?? ""
              }
            })
          }
        })
    }
  }
}
