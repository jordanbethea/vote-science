import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { CreationService } from './creation.service';

describe('CreationService', () => {

  let mockHttp = jasmine.createSpyObj('HttpClient', ['get', 'post']);

  beforeEach(() => TestBed.configureTestingModule({
    providers: [{provide: HttpClient, useValue: mockHttp}]
  }));

  it('should be created', () => {
    const service: CreationService = TestBed.get(CreationService);
    expect(service).toBeTruthy();
  });
});
