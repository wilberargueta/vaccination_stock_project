import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Response } from '../../util/response';
import { User } from '../domain/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  userEventSuscribe: Subject<User> = new Subject();
  private readonly URL_API = '/user';
  constructor(private http: HttpClient) {
  }

  public save(user: User): Observable<Response<User>> {
    return this.http.post<Response<User>>(this.URL_API, user);
  }
  public update(user: User): Observable<Response<User>> {
    return this.http.put<Response<User>>(this.URL_API, user);
  }
  public changePassword(user: User): Observable<Response<User>> {
    return this.http.post<Response<User>>(this.URL_API + '/update-password', user);
  }
  public delete(id: number): Observable<Response<User>> {
    return this.http.delete<Response<User>>(`${this.URL_API}/${id}`);
  }
  public deleteList(user: User[]): Observable<Response<User>> {
    return this.http.post<Response<User>>(`${this.URL_API}/delete/list`, user);
  }
  public getById(id: number): Observable<Response<User>> {
    return this.http.get<Response<User>>(`${this.URL_API}/${id}`);
  }

  public getAll(): Observable<Response<User[]>> {
    return this.http.get<Response<User[]>>(this.URL_API);
  }

  public exists(username: string): Observable<Response<boolean>> {
    return this.http.get<Response<boolean>>(`${this.URL_API}/${username}/exists-username`);
  }

}