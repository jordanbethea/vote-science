import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs';
import { CreationService } from '../slate.service';

import { ViewSlatesComponent } from './view-slates.component';

describe('ViewSlatesComponent', () => {
  let component: ViewSlatesComponent;
  let fixture: ComponentFixture<ViewSlatesComponent>;

  let mockCreationService = jasmine.createSpyObj('CreationService', {
      createNewSlate: {},
      getAllSlates: (new Observable(observer => observer.next([{id: 1, title: "test", creator:"none", questions: null}])))
    })

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewSlatesComponent ],
      providers: [{provide: CreationService, useValue: mockCreationService}]
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
