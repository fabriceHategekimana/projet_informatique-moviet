import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from '../../../services/authentification.service'

@Component({
  selector: 'app-signup-button',
  templateUrl: './signup-button.component.html',
  styleUrls: ['./signup-button.component.css']
})
export class SignupButtonComponent implements OnInit {

  constructor(public auth: AuthentificationService) { }

  ngOnInit(): void {
  }

}
