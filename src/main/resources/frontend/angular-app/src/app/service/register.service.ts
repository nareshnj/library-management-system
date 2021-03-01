import { BaseService } from './base.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService extends BaseService {

  URL_REGISTER_ENTRY = this.HOST + 'register';
  URL_GET_USERS = this.HOST + 'user';


  constructor(private http: HttpClient) {
    super();
  }

  getRegisteredEntryList() : Observable<any> {
    return this.http.get(this.URL_REGISTER_ENTRY);
  }

  getUserList() : Observable<any> {
    return this.http.get(this.URL_GET_USERS);
  }

  createReisterEntry(entry): Observable<any> {
    return this.http.post(this.URL_REGISTER_ENTRY, entry);
  }

}
