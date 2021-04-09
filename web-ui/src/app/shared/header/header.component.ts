import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isTogglerShown = false; // true if the toggler is displayed

  constructor(private location: Location) { }

  ngOnInit(): void {

  }
  
}
