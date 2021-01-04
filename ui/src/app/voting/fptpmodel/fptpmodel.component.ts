import {Component, Input, OnInit} from '@angular/core';
import {Slate} from "../../slate.service";
import {FormBuilder, FormGroup, FormArray} from "@angular/forms";

@Component({
  selector: 'app-fptpmodel',
  templateUrl: './fptpmodel.component.html',
  styleUrls: ['./fptpmodel.component.css']
})
export class FPTPModelComponent implements OnInit {

  @Input()
  slate: Slate | undefined;

  @Input('group')
  fptpGroup: FormGroup | undefined;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.initFormFromSlate();
  }

  initFormFromSlate() {
    if(this.slate == undefined || this.fptpGroup == undefined){ return; }

    let questions = this.fb.array([]);
    for(let q of this.slate.questions) {
      questions.push(
        this.fb.group({
          [q.id ?? 0]: ['']
        }
      ));
    }
    this.fptpGroup.addControl("fptpQuestions", questions);
  }

}
