import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { GroupService } from 'src/app/services/group.service';
import { AuthentificationService } from '../../services/authentification.service'
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute, private userService: UserService, private auth: AuthentificationService) { }

  ngOnInit(): void {
    this.auth.checkIfLogged();
  }

  goToGroups() {
    this.router.navigate(['groups']);
  }

  processGetStarted() {
    if (environment.production) {
      if (this.auth.isLogged) {
        this.goToGroups();
      } else {
        this.auth.login();
      }
    } else {
      this.goToGroups();
    }
  }

}
