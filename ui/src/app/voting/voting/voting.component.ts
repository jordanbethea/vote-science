import { Component, OnInit } from '@angular/core';
import {ActivatedRoute } from '@angular/router';
import {SlateService, Slate} from "../../slate.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-voting',
  templateUrl: './voting.component.html',
  styleUrls: ['./voting.component.css']
})
export class VotingComponent implements OnInit {
  //slateID: number | undefined;
  private sub: any;
  slate: Slate | undefined;

  votingForm = this.fb.group({});

  useFPTP = true

  rawOutput = {}

  constructor(private route: ActivatedRoute, private slateService: SlateService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      let slateID = params['slateID'];
      let slateObservable = this.slateService.loadSlate(slateID);
      slateObservable.subscribe(
        slate => this.slate = slate
      )
    });
  }

  addFormsForModels() {
    //update this as models are added
    //eventually add a way for users to decide which models to use per poll
    if(this.useFPTP) {
      this.votingForm.addControl("fptpModel", this.fb.group({}));
    }
  }

  submitForm() {
    this.rawOutput = this.votingForm.getRawValue()
  }

}
