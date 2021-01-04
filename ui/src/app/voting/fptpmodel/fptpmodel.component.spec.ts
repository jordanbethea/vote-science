import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FPTPModelComponent } from './fptpmodel.component';

describe('FPTPModelComponent', () => {
  let component: FPTPModelComponent;
  let fixture: ComponentFixture<FPTPModelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FPTPModelComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FPTPModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
