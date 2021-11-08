import { TestBed } from '@angular/core/testing';

import { PreviusRouterService } from './previus-router.service';

describe('PreviusRouterService', () => {
  let service: PreviusRouterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PreviusRouterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
