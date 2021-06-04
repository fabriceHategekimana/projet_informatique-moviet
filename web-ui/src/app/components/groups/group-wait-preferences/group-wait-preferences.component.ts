import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { User } from '../../../shared/interfaces/user'
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { fromEventPattern } from 'rxjs';
import { info } from 'node:console';


@Component({
  selector: 'app-group-wait-preferences',
  templateUrl: './group-wait-preferences.component.html',
  styleUrls: ['./group-wait-preferences.component.css']
})
export class GroupWaitPreferencesComponent implements OnInit {

  currentGroup?: Group;
  users: User[] = [];

  constructor(private groupsComponent : GroupsComponent, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    let thenGroupimport = () => {
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
      // import all users:
      this.getAllUsers();
    };

    this.groupsComponent.getGroup(undefined, thenGroupimport);

    // refresh group info every x seconds:
    setInterval(() => {this.groupsComponent.getGroup(undefined, thenGroupimport);}, 1000);
  }

  goToGenres() { // go to the genres selection page
    this.router.navigate(['genres'], {relativeTo: this.route, skipLocationChange: true });
  }

  // get a single user:
  getUser(id : number, then: () => any = () => void 0, onError: () => any = () => void 0 ): void {
    let user: User;
    this.userService.getUser(Number(id))
      .subscribe(r => {
        user = r;
        if (user) { // if the user exist
          if (this.users.findIndex((e) => e.id == user.id) == -1) { // if user isn't already in the list
            this.users.push(user); 
          }
          then();
        } else {
          onError();
        }
      });
  }

  // get all the users:
  getAllUsers() {
    if (this.currentGroup) {
      for (const groupUser of this.currentGroup.users) {
        const userId = groupUser.id;
        this.getUser(userId);
        // TODO: remove user if not in the list anymore
      }
    }
  }

}
