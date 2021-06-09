import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { UsersStatus } from '../../../shared/interfaces/users-status';
import { UserStatusValue } from '../../../shared/interfaces/users-status';
import { UserService } from '../../../services/user.service';
import { GroupService } from 'src/app/services/group.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-group-wait-preferences',
  templateUrl: './group-wait-preferences.component.html',
  styleUrls: ['./group-wait-preferences.component.css']
})
export class GroupWaitPreferencesComponent implements OnInit {

  currentGroup?: Group;

  private timerRefreshResult? :NodeJS.Timeout;

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    let thenGroupimport = () => {
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
    };

    this.groupsComponent.getGroup(undefined, () => {thenGroupimport(); this.setUserStatus();});

    // refresh group info every x micro-seconds:
    this.timerRefreshResult = setInterval(() => {this.groupsComponent.getGroup(undefined, thenGroupimport);}, 1000);
  }

  goToGenres() { // go to the genres selection page
    this.clearTimer();    
    this.router.navigate(['genres'], {relativeTo: this.route, skipLocationChange: true });
  }

  goToFindMatch() { // go to the find-match page
    this.clearTimer();
    this.router.navigate(['find-match'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  goToFindMatchIfVoting(groupStatus: any) { // go to the voting page if the users are voting
    // console.log(isVoting);
    if(groupStatus == UserStatusValue.VOTING || groupStatus == UserStatusValue.DONE) {
      this.clearTimer();
      this.goToFindMatch();
    }
  }

  skipPref() {
    // TODO: only the admin should skip
    if (this.currentGroup != undefined) {
      // change the group status
      this.groupService.setGroupStatus(this.currentGroup.id, UserStatusValue.VOTING)
        .subscribe(()=>{
          this.goToFindMatch(); // go to find match
        }); // reset to choosing
    }
  }

  getMyUserId(): string { //! Temporary
    return '1';
  }

  setUserStatus(then: () => any = () => void 0, onError?: () => any) {
    this.groupsComponent.getGroup(undefined, () => {
        let groupId = this.groupsComponent.currentGroup!.id;
        this.groupService.getGroupStatus(groupId)
          .subscribe((groupStatus) => {
            if (groupStatus == UserStatusValue.CHOOSING) {
              this.groupService.setUserStatus(groupId, this.getMyUserId(), UserStatusValue.READY) // change status to READY
                .subscribe(()=> {
                  then();
                }, () => {
                  if (onError == undefined) {
                    then();
                  } else {
                    onError();
                  }
                }); 
            } else {
              if (onError == undefined) {
                then();
              } else {
                onError();
              }
            }
          }, () => {
            if (onError == undefined) {
              then();
            } else {
              onError();
            }
          });
    });
  }

  clearTimer() {
    if(this.timerRefreshResult!=undefined) clearInterval(this.timerRefreshResult);
  }
}
