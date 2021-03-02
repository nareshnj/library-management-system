import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BaseService {

  HOST: string = 'http://localhost:8080/v1/';

  constructor() { }
}
