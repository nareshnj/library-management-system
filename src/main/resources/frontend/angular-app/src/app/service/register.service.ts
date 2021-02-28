import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  URL_REGISTER_ENTRY = 'http://localhost:8080/v1/register';
  URL_GET_USERS = 'http://localhost:8080/v1/user';
  URL_GET_BOOKS = 'http://localhost:8080/v1/book'

  constructor(private http: HttpClient) { }

  getRegisteredEntryList() : Observable<any> {
    return this.http.get(this.URL_REGISTER_ENTRY);
  }

  getUserList() : Observable<any> {
    return this.http.get(this.URL_GET_USERS);
  }

  getBookList() : Observable<any> {
    return this.http.get(this.URL_GET_BOOKS);
  }

  createReisterEntry(entry): Observable<any> {
    return this.http.post(this.URL_REGISTER_ENTRY, entry);
  }

}
