import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { SlateService } from './slate.service';

describe('SlateService', () => {

  let mockHttp = jasmine.createSpyObj('HttpClient', ['get', 'post']);

  beforeEach(() => TestBed.configureTestingModule({
    providers: [{provide: HttpClient, useValue: mockHttp}]
  }));

  it('should be created', () => {
    const service: SlateService = TestBed.get(SlateService);
    expect(service).toBeTruthy();
  });
});
