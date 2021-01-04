import { JsonPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { SlateService, Slate } from '../slate.service';


@Component({
  selector: 'app-view-slates',
  templateUrl: './view-slates.component.html',
  styleUrls: ['./view-slates.component.css']
})
export class ViewSlatesComponent implements OnInit {

  constructor(private creationService:SlateService) { }

  slates:Array<Slate> = new Array<Slate>();

  ngOnInit() {
    this.creationService.getAllSlates().subscribe({
      next: x =>  {
        this.slates = x;
        console.log("service response: " + JSON.stringify(x));
      },
      error: error => console.log(error)
    });
  }

}
