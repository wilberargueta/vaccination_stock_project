import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employee } from '../domain/employee';
import { Observable } from 'rxjs';
import { Response } from '../../util/response';
import { User } from '../../access/domain/user';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private readonly URL_API = '/employee';
  constructor(private http: HttpClient) { }

  public save(employee: Employee): Observable<Response<Employee>> {
    return this.http.post<Response<Employee>>(this.URL_API, employee);
  }
  public update(employee: Employee): Observable<Response<Employee>> {
    return this.http.put<Response<Employee>>(this.URL_API, employee);
  }
  public delete(id: number): Observable<Response<Employee>> {
    return this.http.delete<Response<Employee>>(`${this.URL_API}/${id}`);
  }

  public deleteList(employees: Employee[]): Observable<Response<Employee>> {
    return this.http.post<Response<Employee>>(`${this.URL_API}/delete/list`, employees);
  }
  public getById(id: number): Observable<Response<Employee>> {
    return this.http.get<Response<Employee>>(`${this.URL_API}/${id}`);
  }

  public getAll(): Observable<Response<Employee[]>> {
    return this.http.get<Response<Employee[]>>(this.URL_API);
  }
  public getByState(state: boolean): Observable<Response<Employee[]>> {
    return this.http.get<Response<Employee[]>>(`${this.URL_API}/vaccination/${state}/state`);
  }
  public getByType(type: string): Observable<Response<Employee[]>> {
    return this.http.get<Response<Employee[]>>(`${this.URL_API}/vaccination/${type}/type`);
  }
  public getByDate(start: string, end: string): Observable<Response<Employee[]>> {
    return this.http.get<Response<Employee[]>>(`${this.URL_API}/vaccination/${start}/${end}/date`);
  }

  public validateDocumentNumber(documentNumber: string): Observable<Response<Employee>> {
    return this.http.get<Response<Employee>>(`${this.URL_API}/${documentNumber}/validate-document-number`);
  }
  public updateVaccinate(id: number, state: boolean): Observable<Response<boolean>> {
    return this.http.put<Response<boolean>>(`${this.URL_API}/update/${id}/${state}/vaccinated`, null);
  }

  public getByUserAccess(user: User): Observable<Response<Employee>> {
    return this.http.post<Response<Employee>>(`${this.URL_API}/by-user`, user);
  }
}
