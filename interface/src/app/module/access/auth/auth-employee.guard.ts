import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionStorageService } from '../../util/service/session-storage.service';
import { JwtToken } from '../domain/jwt-token';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class AuthEmployeeGuard implements CanActivate {
  constructor(
    private sessionService: SessionStorageService,
    private router: Router
  ) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.checkLogin();
  }
  checkLogin(): true | UrlTree {

    const sessionJWT: JwtToken = this.sessionService.getValue('session_token');

    if (!this.sessionService.isSessionActive())
      return this.router.parseUrl('/login');

    if (sessionJWT.role.rol !== 'EMPLOYEE')
      return this.router.parseUrl('/access-denied');

    return true;
  }

}
