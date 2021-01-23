import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VotingComponent } from './voting.component';
import {ActivatedRoute} from "@angular/router";
import {FormBuilder} from "@angular/forms";

describe('VotingComponent', () => {
  let component: VotingComponent;
  let fixture: ComponentFixture<VotingComponent>;

  const fakeActivatedRoute = {
    snapshot: { data: {} }
  }

  /*beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VotingComponent ],
      providers: [{ provide: ActivatedRoute, useClass: fakeActivatedRoute}, FormBuilder]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VotingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  }); */
});
