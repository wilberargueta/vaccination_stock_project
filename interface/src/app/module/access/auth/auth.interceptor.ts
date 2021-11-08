import {
    HttpEvent, HttpHandler,
    HttpInterceptor, HttpRequest, HttpResponse
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SessionStorageService } from '../../util/service/session-storage.service';
import { JwtToken } from '../domain/jwt-token';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(
        private sessionService: SessionStorageService,
        private router: Router
    ) { }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const tokenJWT: JwtToken = this.sessionService.getValue('session_token');

        if (this.sessionService.isSessionActive()) {

            const authReq = request.clone({ headers: request.headers.set('Authorization', `${tokenJWT.token_type} ${tokenJWT.token}`).set('rejectUnauthorized', 'false') });
            return next.handle(authReq).pipe(map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse &&
                    event.status === 200 && event['body']['code'] === '403'
                ) {
                    this.sessionService.delete('session_token');
                    this.sessionService.delete('session_time');
                    this.router.navigate(['/login']);
                }
                return event;
            }));
        } else
            console.log('no active');

        return next.handle(request);
    }

}
