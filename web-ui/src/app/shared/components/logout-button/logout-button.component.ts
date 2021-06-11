import { Component, OnInit } from '@angular/core';
import { AuthentificationService } from '../../../services/authentification.service'

@Component({
  selector: 'app-logout-button',
  templateUrl: './logout-button.component.html',
  styleUrls: ['./logout-button.component.css']
})
export class LogoutButtonComponent implements OnInit {

  constructor(public auth: AuthentificationService) { }

  ngOnInit(): void {
  }

}
