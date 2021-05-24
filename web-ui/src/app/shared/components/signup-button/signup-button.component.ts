import { Component, OnInit } from '@angular/core';

import { AuthService } from '@auth0/auth0-angular';

@Component({
  selector: 'app-signup-button',
  templateUrl: './signup-button.component.html',
  styleUrls: ['./signup-button.component.css']
})
export class SignupButtonComponent implements OnInit {
  constructor(public auth: AuthService) {}

  ngOnInit(): void {}

  loginWithRedirect(): void {
    this.auth.loginWithRedirect({ screen_hint: 'signup' });
  }
}