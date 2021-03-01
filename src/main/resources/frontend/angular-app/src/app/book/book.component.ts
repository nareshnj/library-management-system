import { BookService } from './../service/book.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  bookList: any[];

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.bookList = [];
    this.getBookList();
  }

  getBookList() {
    this.bookService.getBookList().subscribe(response => {
      this.bookList = response;
    })
  }

}
