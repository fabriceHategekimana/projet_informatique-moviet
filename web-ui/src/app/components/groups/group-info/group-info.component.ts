import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { User } from '../../../shared/interfaces/user'
import { UserService } from '../../../services/user.service';
import { GroupService } from 'src/app/services/group.service';
import { UsersStatus } from '../../../shared/interfaces/users-status'
import { UserStatusValue } from '../../../shared/interfaces/users-status'
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

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    let thenGroupimport = () => {
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
      // import all users:
      this.getAllUsers();
    };

    this.groupsComponent.getGroup(undefined, () => {
        thenGroupimport();
        this.groupService.addUserToGroup(this.currentGroup!.id);
        this.autoNavigate(); // auto navigate
      });

    // refresh group info every x seconds:
    setInterval(() => {this.groupsComponent.getGroup(undefined, thenGroupimport);}, 1000);
  }

  goToGenres() { // go to the genres selection page
    this.router.navigate(['genres'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  // get a single user:
  getUser(id : string, then: () => any = () => void 0, onError: () => any = () => void 0 ): void {
    let user: User;
    this.userService.getUser(id)
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

  autoNavigate() { // redirect the user depending on the status of the group
    /*
    * group CHOOSING + user CHOOSING => group-info
    * group CHOOSING + user READY => wait-pref
    * group VOTING + user VOTING => find-match
    * group VOTING + user DONE => wait-voting
    * group DONE => group-wait-result
    */
    if (this.currentGroup != undefined) {
      let groupId = this.currentGroup.id;
      this.groupService.getGroupStatus(groupId)
        .subscribe((groupStatus) => {
          switch (groupStatus) {
            case UserStatusValue.CHOOSING:
              this.groupService.getUserStatus(groupId, this.getMyUserId())
                .subscribe((userStatus) => {
                  if (userStatus == UserStatusValue.READY) {
                    //* Go to wait-pref
                    this.goToWaitPref();
                  }
                });
              break;

            case UserStatusValue.VOTING:
              this.groupService.getUserStatus(groupId, this.getMyUserId())
                .subscribe((userStatus) => {
                  if (userStatus == UserStatusValue.VOTING) {
                    //* Go to find-match
                    this.goToFindMatch();
                  } else {
                    //* Go to wait-voting
                    this.goToWaitVoting();
                  }
                });
              break;

            case UserStatusValue.DONE:
              //* Go to wait-result
              this.goToWaitResult();
              break;
                
            default:
              break;
          }
        });
    }
  }

  getMyUserId(): string { //! Temporary
    return '1';
  }

  goToFindMatch() { // go to the find-match page
    this.router.navigate(['find-match'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  goToWaitPref() { // go to the wat-pref page
    this.router.navigate(['wait-pref'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  goToWaitVoting() { // go to the wait voting page
    this.router.navigate(['wait-voting'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  goToWaitResult() { // go to the wait-result page
    this.router.navigate(['wait-result'], {relativeTo: this.route.parent, skipLocationChange: true});
  }
}
