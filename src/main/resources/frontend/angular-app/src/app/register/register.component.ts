import { BookService } from './../service/book.service';
import { Component, OnInit } from '@angular/core';
import { RegisterService } from '../service/register.service';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerEntryList: any[];
  userList: any[];
  bookList: any[];
  entryType: any[];
  entryForm: any;
  dropdownSettings: IDropdownSettings;
  response: any;

  constructor(private registerService: RegisterService, private bookService: BookService) { }

  ngOnInit(): void {
    this.response = {};
    this.resetEntryForm();
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      limitSelection: 2,
      allowSearchFilter: true
    };
    this.resetEntryForm();
    this.entryType = [{id:'BORROW', value: 'Borrow'}, {'id': 'RETURN', value: 'Return'}];
    this.getRegisterEntries();
    this.getUserList();

  }


  getRegisterEntries() {
    this.registerService.getRegisteredEntryList().subscribe(response => {
      this.registerEntryList = response;

      this.registerEntryList.forEach(entry => {
        let books = [];
        entry.bookEntries.forEach(bookEntry => {
          books.push(bookEntry.book.name);
        });
        entry.bookNames = books;
      });
    })
  }

  getUserList() {
    this.registerService.getUserList().subscribe(response => {
      this.userList = response;
    })
  }

  getBookList() {
    this.bookService.getBookList().subscribe(response => {
      this.bookList = response;
    })
  }

  submitRegisterEntry() {
    this.entryForm.books = this.entryForm.books.map(book => book.id)
    this.registerService.createReisterEntry(this.entryForm).subscribe((data) => {
      this.response = data;
      this.getRegisterEntries();
      this.response.class = 'alert alert-success pt-1 pb-1';
      this.resetEntryForm();
    },
    (error) => {
      this.response = error.error;
      this.response.class = 'alert alert-danger pt-1 pb-1';
      this.resetEntryForm();
    });
  }

  resetEntryForm() {
    this.entryForm = {userId : -1, entryType: -1, books: []};
  }

  loadAvailableBookList(value) {
    if(value == 'BORROW') {
      this.getBookList();
    } else {
      this.getBorrowedBooksByUser();
    }
  }

  getBorrowedBooksByUser() {
    this.bookService.getBorrowedBooksByUser(this.entryForm.userId).subscribe(data => {
      this.bookList = data;
     });
  }

  isValidForm() {
    return this.entryForm.userId != -1 && this.entryForm.entryType != -1 && this.entryForm.books.length != 0;
  }
}
