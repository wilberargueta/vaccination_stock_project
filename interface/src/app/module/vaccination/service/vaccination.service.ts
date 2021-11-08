import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Response } from '../../util/response';
import { VaccinationStock } from '../domain/vaccination-stock';

@Injectable({
  providedIn: 'root'
})
export class VaccinationService {
  vaccinationEventSuscribe: Subject<VaccinationStock> = new Subject();
  private readonly URL_API = '/vaccination-stock';
  constructor(private http: HttpClient) { }


  public save(vaccinationStock: VaccinationStock): Observable<Response<VaccinationStock>> {
    return this.http.post<Response<VaccinationStock>>(this.URL_API, vaccinationStock);
  }
  public update(vaccinationStock: VaccinationStock): Observable<Response<VaccinationStock>> {
    return this.http.put<Response<VaccinationStock>>(this.URL_API, vaccinationStock);
  }
  public delete(id: number): Observable<Response<VaccinationStock>> {
    return this.http.delete<Response<VaccinationStock>>(`${this.URL_API}/${id}`);
  }
  public deleteList(vaccinationStock: VaccinationStock[]): Observable<Response<VaccinationStock>> {
    return this.http.post<Response<VaccinationStock>>(`${this.URL_API}/delete/list`, vaccinationStock);
  }
  public getById(id: number): Observable<Response<VaccinationStock>> {
    return this.http.get<Response<VaccinationStock>>(`${this.URL_API}/${id}`);
  }

  public getByEmployeeId(id: number): Observable<Response<VaccinationStock[]>> {
    return this.http.get<Response<VaccinationStock[]>>(`${this.URL_API}/${id}/employee`);
  }

  public getAll(): Observable<Response<VaccinationStock[]>> {
    return this.http.get<Response<VaccinationStock[]>>(this.URL_API);
  }


  public getVaccinateOptions(): { name: string, value: string, id: number }[] {
    return [{ name: 'Sputnik', value: 'Sputnik', id: 1 },
    { name: 'AstraZeneca', value: 'AstraZeneca', id: 2 },
    { name: 'Pfizer', value: 'Pfizer', id: 3 },
    { name: 'Jhonson & Jhonson', value: 'Jhonson & Jhonson', id: 4 }];
  }

}
