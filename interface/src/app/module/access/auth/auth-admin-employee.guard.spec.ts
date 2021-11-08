import { TestBed } from '@angular/core/testing';

import { AuthAdminEmployeeGuard } from './auth-admin-employee.guard';

describe('AuthAdminEmployeeGuard', () => {
  let guard: AuthAdminEmployeeGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AuthAdminEmployeeGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
