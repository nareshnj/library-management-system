import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BaseService } from './base.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BookService extends BaseService {

  URL_GET_BORROWED_BOOKS = this.HOST + 'register/entry';
  URL_GET_BOOKS = this.HOST + 'book';

  constructor(private http: HttpClient) {
    super();
   }


  getBorrowedBooksByUser(userId): Observable<any> {
    return this.http.get(this.URL_GET_BORROWED_BOOKS + '/' + userId);
  }

  getBookList() : Observable<any> {
    return this.http.get(this.URL_GET_BOOKS);
  }
}
