import { TestBed } from '@angular/core/testing';

import { CreationService } from './creation.service';

describe('CreationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreationService = TestBed.get(CreationService);
    expect(service).toBeTruthy();
  });
});
