import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { CreationService } from '../creation.service';

import { CreateSlateComponent } from './create-slate.component';

describe('CreateSlateComponent', () => {
  let component: CreateSlateComponent;
  let fixture: ComponentFixture<CreateSlateComponent>;

  //let mockFormBuilder = jasmine.createSpyObj('mockForm', ['group', 'array'])
  let mockCreationService = jasmine.createSpyObj('mockCreation', ['createNewSlate'])

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSlateComponent ],
      providers: [
        FormBuilder,
        //{provide: FormBuilder, useValue: mockFormBuilder},
        {provide: CreationService, useValue: mockCreationService}]
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
