import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormArray, FormControl, FormGroup } from '@angular/forms';
import { CreationService, Slate } from '../creation.service';


@Component({
  selector: 'app-create-slate',
  templateUrl: './create-slate.component.html',
  styleUrls: ['./create-slate.component.css']
})
export class CreateSlateComponent implements OnInit {

  localJson = JSON;

  constructor(private fb: FormBuilder, private createService: CreationService) { }

  slateForm = this.fb.group({
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

  get questions(): FormArray {
    return this.slateForm.get('questions') as FormArray
  }

 /**
   * Returns list of candidates for a given question
   * @param question number representing the position of the question in the form array
   */
  candidates(question: number): FormArray {
    return this.questions.controls[question].get('candidates') as FormArray
  }

  addQuestion() {
    console.log(`num questions: ${(this.slateForm.get('questions') as FormArray)?.length}`);
    this.questions.push(
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
    console.log(`num questions: ${(this.slateForm.get('questions') as FormArray)?.length}`);
  }

  addCandidate(questionPos: number) {
    this.candidates(questionPos).push(
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
        title: this.slateForm.get('title')?.value ?? "",  //if title exists get value - if this is null, use blank string instead. optional chaining/nullish coalescing
        creator: this.slateForm.get('creator')?.value ?? "",
        questions: (this.slateForm.get('questions') as FormArray).controls.map(questionForm => {
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
