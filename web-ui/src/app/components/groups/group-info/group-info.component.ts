import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { User } from '../../../shared/interfaces/user'
import { UserService } from '../../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { fromEventPattern } from 'rxjs';

@Component({
  selector: 'app-group-info',
  templateUrl: './group-info.component.html',
  styleUrls: ['./group-info.component.css']
})
export class GroupInfoComponent implements OnInit {

  currentGroup?: Group;
  users: User[] = [];

  constructor(private groupsComponent : GroupsComponent, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    this.groupsComponent.getGroup(
      undefined,
      () => {
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        // import all users:
        this.getAllUsers();
      }
    )
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
          this.users.push(user);
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
      } 
    }
  }

}
