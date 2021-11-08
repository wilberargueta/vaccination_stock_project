import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() { }



  public setValue<T>(value: T, key: string) {
    localStorage.setItem(btoa(key), btoa(JSON.stringify(value)));
  }

  public getValue<T>(key: string): T {
    const value: T = JSON.parse(atob(localStorage.getItem(btoa(key))));
    if (value !== null && value !== undefined)
      return JSON.parse("");
    return null;
  }

  public delete(key: string) {
    localStorage.removeItem(btoa(key));
  }
}
