import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSlateComponent } from './create-slate.component';

describe('CreateSlateComponent', () => {
  let component: CreateSlateComponent;
  let fixture: ComponentFixture<CreateSlateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSlateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSlateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
