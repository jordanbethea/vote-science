import {Component, Input, OnInit, OnChanges} from '@angular/core';
import {Slate} from "../../slate.service";
import {FormBuilder, FormGroup, FormArray} from "@angular/forms";
import {CommonModule} from "@angular/common";

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

  fptpQuestions: FormArray = this.fb.array([]);

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
  }

  ngOnChanges(): void {
    this.initFormFromSlate();
  }

  initFormFromSlate() {
    if(this.slate == undefined || this.fptpGroup == undefined){
      console.log(`slate: ${this.slate}, fptpGroup: ${this.fptpGroup}`);
      return;
    }

    for(let q of this.slate.questions) {
      this.fptpQuestions.push(
        this.fb.group({
          //[q.id ?? 0]: ['']    -- too clever, can't pull the qid when getting the form values
          questionID: q.id,
          candidateID: ''
        }
      ));
    }
    this.fptpGroup.addControl("fptpQuestions", this.fptpQuestions);
  }

}
