import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
// Import the AuthService type from the SDK
import { AuthService } from '@auth0/auth0-angular';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isTogglerShown = false; // true if the toggler is displayed

  constructor(public auth: AuthService, private location: Location) { }

  ngOnInit(): void {

  }
  
  loginWithRedirect(): void {
    // Call this to redirect the user to the login page
    this.auth.loginWithRedirect();
  }

  // signUp(): void {
  //   this.auth.loginWithRedirect({ screen_hint: 'signup' });
  // }

  logout(): void {
    // Call this to log the user out of the application
    this.auth.logout({ returnTo: window.location.origin });
  }
}
