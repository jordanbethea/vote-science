import { Component, OnInit } from '@angular/core';
import {ActivatedRoute } from '@angular/router';
import {SlateService, Slate} from "../../slate.service";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {Ballot, BallotDetails, FPTPModel, VotingService} from "../voting.service";

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

  useFPTP = true;

  rawOutput = {}

  constructor(private route: ActivatedRoute, private slateService: SlateService, private fb: FormBuilder,
              private voting: VotingService) { }

  ngOnInit(): void {
    this.addFormsForModels();
    this.sub = this.route.paramMap.subscribe(params => {
      let slateIDraw = params.get('slateID');
      if(slateIDraw == undefined){
        console.log("Invalid slate id")
      } else {
        let slateID: number = +slateIDraw;
        let slateObservable = this.slateService.loadSlate(slateID);
        slateObservable.subscribe(
          slate => this.slate = slate
        )
      }
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
    let ballot:Ballot = {
      details: this.createBallotFromForm(),
      fptpModel: this.createFPTPModelFromForm()
    }
    this.voting.saveBallot(ballot)
  }

  createFPTPModelFromForm(): FPTPModel {
    return {
      choices: (this.votingForm.get('fptpModel')?.get('fptpQuestions') as FormArray).controls.map(fptpChoice => {
        return {
          ballotID: this.votingForm.get('id')?.value ?? -1,
          questionID: fptpChoice.get('questionID')?.value ?? -1,
          candidateID: fptpChoice.get('candidateID')?.value ?? -1
        }
      })
    }
  }

  createBallotFromForm() : BallotDetails {
    return {
      id: this.votingForm.get('id')?.value ?? -1,
      voter: this.votingForm.get('voter')?.value ?? "",
      slateID: this.votingForm.get('slateID')?.value ?? -1
    }
  }

}
