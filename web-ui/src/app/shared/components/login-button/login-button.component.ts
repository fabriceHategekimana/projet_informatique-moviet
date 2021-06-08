import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from '../../../services/authentification.service'

@Component({
  selector: 'app-login-button',
  templateUrl: './login-button.component.html',
  styleUrls: ['./login-button.component.css']
})
export class LoginButtonComponent implements OnInit {

  constructor(public auth: AuthentificationService) { }

  ngOnInit(): void {
  }

}
