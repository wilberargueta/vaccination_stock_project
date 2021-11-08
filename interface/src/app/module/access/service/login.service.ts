import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from '../../util/response';
import { JwtToken } from '../domain/jwt-token';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private readonly URL_API = '/oauth';
  constructor(private http: HttpClient) { }

  public getToken(username: string, password: string): Observable<Response<JwtToken>> {
    return this.http.post<Response<JwtToken>>(`${this.URL_API}/token`, { username, password });
  }
}
