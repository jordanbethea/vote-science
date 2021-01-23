import { TestBed } from '@angular/core/testing';

import { VotingService } from './voting.service';
import {HttpClient} from "@angular/common/http";

describe('VotingService', () => {
  let service: VotingService;

  let mockHttp = jasmine.createSpyObj('HttpClient', ['get', 'post']);

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{provide: HttpClient, useValue: mockHttp}]
    });
    service = TestBed.inject(VotingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
