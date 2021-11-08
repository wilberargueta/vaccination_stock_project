import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from '../../util/response';
import { Rol } from '../domain/rol';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  private readonly URL_API = '/role';
  constructor(private http: HttpClient) { }

  public getAll(): Observable<Response<Rol[]>> {
    return this.http.get<Response<Rol[]>>(this.URL_API);
  }
}
