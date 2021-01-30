import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VotingComponent } from './voting/voting.component';
import { FPTPModelComponent } from './fptpmodel/fptpmodel.component';
import {ReactiveFormsModule} from "@angular/forms";
import { ResultsComponent } from './results/results.component';



@NgModule({
  declarations: [VotingComponent, FPTPModelComponent, ResultsComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ]
})
export class VotingModule { }
