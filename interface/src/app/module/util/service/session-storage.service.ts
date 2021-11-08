import { toBase64String } from '@angular/compiler/src/output/source_map';
import { Injectable } from '@angular/core';
import * as moment from 'moment';
import { JwtToken } from '../../access/domain/jwt-token';
import { Employee } from '../../employee/domain/employee';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {

  private employee: Employee;

  constructor() { }


  public setValue<T>(value: T, key: string) {
    sessionStorage.setItem(btoa(key), btoa(JSON.stringify(value)));
  }

  public getValue<T>(key: string): T {
    const valueRaw: string = sessionStorage.getItem(btoa(key));
    if (valueRaw === null || valueRaw === undefined)
      return null;
    const value: T = JSON.parse(atob(valueRaw));
    if (value !== null && value !== undefined)
      return value;
    return null;
  }

  public delete(key: string) {
    sessionStorage.removeItem(btoa(key));
  }
  public setEmployee(employee: Employee) {
    this.employee = employee;
  }
  public getEmployee() {
    return this.employee;
  }
  public isSessionActive(): boolean {

    const tokenJWT: JwtToken = this.getValue('session_token');
    if (tokenJWT === null)
      return false;
    const time: string = this.getValue('session_time');
    if (time === null)
      return false;
    if (moment().diff(moment(time), 'minutes') > 60)
      return false;

    return true;
  }
}
