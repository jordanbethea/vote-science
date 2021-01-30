import { Component, OnInit } from '@angular/core';
import {Ballot, VotingService} from "../voting.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {

  castBallots: Array<Ballot> = [];

  constructor(private voting: VotingService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe( params => {
      let slateIDraw = params.get('slateID');
      if(slateIDraw == undefined){
        console.log("Invalid slate id");
      } else {
        let slateID: number = +slateIDraw
        this.voting.getSlateResults(slateID).subscribe(ballots =>
          this.castBallots = ballots
        );
      }
    })

  }

}
