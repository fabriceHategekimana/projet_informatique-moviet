import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';

const cookieName = "_oauth2_proxy_csrf";

const loginPage = "https://moviet.graved.ch/oauth2/start?rd=%2Fhome%2F";

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  public isLogged = false;

  constructor(private router: Router) { }

  checkIfLogged(): boolean {
    // test if the cookie exist
    if (document.cookie.indexOf(cookieName) > -1 ) {
      this.isLogged = true;
      return true;
    } else {
      this.isLogged = false;
      return false;
    }
  }

  deleteCookie(name: string) {
    document.cookie = name + "= ; expires = Thu, 01 Jan 1970 00:00:00 GMT";
  }

  logout() {
    this.deleteCookie(cookieName);
    this.checkIfLogged();
    this.router.navigate(['']); // navigate to the home page
  }

  login() {
    window.location.href = loginPage;
    this.checkIfLogged();
  }

  signUp() {
    //TODO: pass signup parameter if possible
    window.location.href = loginPage;
    this.checkIfLogged();
  }
}
