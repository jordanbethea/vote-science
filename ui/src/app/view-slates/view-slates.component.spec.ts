import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewSlatesComponent } from './view-slates.component';

describe('ViewSlatesComponent', () => {
  let component: ViewSlatesComponent;
  let fixture: ComponentFixture<ViewSlatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewSlatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewSlatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
