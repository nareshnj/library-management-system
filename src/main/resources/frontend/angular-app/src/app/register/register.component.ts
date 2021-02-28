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

  constructor(private registerService: RegisterService) { }

  ngOnInit(): void {
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'id',
      textField: 'name',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      limitSelection: 2,
      allowSearchFilter: true
    };
    this.entryForm = {userId : -1, entryType: '-1', books: []};
    this.entryType = [{id:'BORROW', value: 'Borrow'}, {'id': 'RETURN', value: 'Return'}];
    this.getRegisterEntries();
    this.getUserList();
    this.getBookList();
  }


  getRegisterEntries() {
    this.registerService.getRegisteredEntryList().subscribe(response => {
      this.registerEntryList = response;
    })
  }

  getUserList() {
    this.registerService.getUserList().subscribe(response => {
      this.userList = response;
    })
  }

  getBookList() {
    this.registerService.getBookList().subscribe(response => {
      this.bookList = response;
    })
  }

  submitRegisterEntry() {
    this.entryForm.books = this.entryForm.books.map(book => book.id)
    console.log('-----------'+ JSON.stringify(this.entryForm));
    this.registerService.createReisterEntry(this.entryForm).subscribe(response => {
      alert(response);
    });
    this.entryForm = {userId : -1, entryType: '-1', books: []};
  }
}
