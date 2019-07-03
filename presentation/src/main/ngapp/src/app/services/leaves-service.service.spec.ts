import { TestBed } from '@angular/core/testing';

import { LeavesServiceService } from './leaves-service.service';

describe('LeavesServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LeavesServiceService = TestBed.get(LeavesServiceService);
    expect(service).toBeTruthy();
  });
});
