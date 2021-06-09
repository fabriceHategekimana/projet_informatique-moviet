import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { AuthentificationService } from '../../../services/authentification.service'

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isTogglerShown = false; // true if the toggler is displayed

  isLogged = false;

  constructor(private location: Location, public auth: AuthentificationService) { }

  ngOnInit(): void {
    this.auth.checkIfLogged();
  }
  
}
