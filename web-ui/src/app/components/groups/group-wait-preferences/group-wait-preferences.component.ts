import { Component, OnInit, OnDestroy } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { UsersStatus } from '../../../shared/interfaces/users-status';
import { UserStatusValue } from '../../../shared/interfaces/users-status';
import { UserService } from '../../../services/user.service';
import { GroupService } from 'src/app/services/group.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/shared/interfaces/user';


@Component({
  selector: 'app-group-wait-preferences',
  templateUrl: './group-wait-preferences.component.html',
  styleUrls: ['./group-wait-preferences.component.css']
})
export class GroupWaitPreferencesComponent implements OnInit, OnDestroy {

  currentGroup?: Group;

  myUser?: User;

  isAdmin: boolean = false;

  private timerRefreshResult? :NodeJS.Timeout;

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    let thenGroupimport = () => {
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
      this.getMyUser(() => {
        this.testIfAdmin();
      });
    };

    this.groupsComponent.getGroup(undefined, () => {thenGroupimport(); this.setUserStatus();});

    // refresh group info every x micro-seconds:
    this.timerRefreshResult = setInterval(() => {this.groupsComponent.getGroup(undefined, thenGroupimport);}, 1000);
  }

  ngOnDestroy() {
    if (this.timerRefreshResult != undefined) {
      clearInterval(this.timerRefreshResult); 
    }
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

  endChoosing() {
    //TODO: test if admin:
    if (this.isAdmin) {
      this.skipPref(); 
    }
  }

  getMyUserId(): string {
    if (this.myUser != undefined) {
      return this.myUser.id;
    } else {
      return '';
    }
  }

  getMyUser(then: ()=>any = ()=>void 0) {
    this.userService.whoAmI()
      .subscribe((user) => {
        this.myUser = user;
        then();
      });
  }


  testIfAdmin() { // test if the user is admin
    if (this.currentGroup != undefined) {
      if (this.myUser != undefined) {
        if (this.myUser.id == this.currentGroup.admin_id) {
          this.isAdmin = true;
        } else {
          this.isAdmin = false;
        }
      }
    }
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
