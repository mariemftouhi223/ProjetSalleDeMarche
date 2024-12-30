import { TestBed } from '@angular/core/testing';

import { ServicesTsService } from '../services.ts.service';

describe('ServicesTsService', () => {
  let service: ServicesTsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicesTsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
