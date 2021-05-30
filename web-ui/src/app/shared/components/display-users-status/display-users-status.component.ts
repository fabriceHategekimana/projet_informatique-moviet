import { Component, Input, OnInit, Output, EventEmitter, OnChanges } from '@angular/core';
import { GroupsComponent } from '../../../components/groups/groups.component'
import { Group } from '../../../shared/interfaces/group';
import { User } from '../../../shared/interfaces/user'
import { UserService } from '../../../services/user.service';
import { GroupService } from '../../../../app/services/group.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersStatus } from '../../../shared/interfaces/users-status'
import { UserStatusValue } from '../../../shared/interfaces/users-status'

@Component({
  selector: 'app-display-users-status',
  templateUrl: './display-users-status.component.html',
  styleUrls: ['./display-users-status.component.css']
})
export class DisplayUsersStatusComponent implements OnInit, OnChanges {

  @Input() currentGroup?: Group; // currentGroup as an input parameter of the child

  @Output() isVotingEvent = new EventEmitter<boolean>(); // indicate that we need to switch to the voting page if we are on the waiting page for preferencies

  users: User[] = [];
  usersStatus : UsersStatus = {}; // map user id and status

  constructor(private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
      // import all users:
      this.getAllUsers();
      // import all user status:
      this.getUsersStatus();

      this.testIfVoting();
  }

  ngOnChanges(): void { // when the input changes
      // import all users:
      this.getAllUsers();
      // import all user status:
      this.getUsersStatus();

      this.testIfVoting();
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


  getUsersStatus() {
    // this.usersStatus = {  //!Mock
    //   0: UserStatusValue.READY,
    //   1: UserStatusValue.CHOOSING,
    //   2: UserStatusValue.READY,
    //   3: UserStatusValue.CHOOSING,
    //   4: UserStatusValue.READY,
    //   5: UserStatusValue.CHOOSING,
    // };
    if (this.currentGroup !== undefined) {
      this.groupService.getUsersStatus(this.currentGroup.id)
      .subscribe(usersStatus => {
        this.usersStatus = usersStatus;
      }); 
    }
  }

  getStatusText(userStatusValue: UserStatusValue): string { // text to show according to the status
    switch(userStatusValue){
      case UserStatusValue.CHOOSING:
        return "waiting";
      case UserStatusValue.READY:
        return "ready";
      case UserStatusValue.VOTING:
        return "voting";
      case UserStatusValue.DONE:
        return "done";
      default:
        return "error";
    }
  }

  getStatusIconPath(userStatusValue: UserStatusValue): string { // path to the image according to the status
    switch(userStatusValue){
      case UserStatusValue.CHOOSING:
        return '../../../../assets/icons/Rolling-1s-200px.svg';
      case UserStatusValue.READY:
        return '../../../../assets/icons/checkbox-circle-fill.svg';
      case UserStatusValue.VOTING:
        return '../../../../assets/icons/Rolling-1s-200px.svg';
      case UserStatusValue.DONE:
        return '../../../../assets/icons/checkbox-circle-fill.svg';
      default:
        return '../../../../assets/icons/Rolling-1s-200px.svg';
    }
  }

  getStatusTextColor(userStatusValue: UserStatusValue): string { // color of the text according to the status
    switch(userStatusValue){
      case UserStatusValue.CHOOSING:
        return 'red';
      case UserStatusValue.READY:
        return '#2FCC71';
      case UserStatusValue.VOTING:
        return 'red';
      case UserStatusValue.DONE:
        return '#2FCC71';
      default:
        return 'red';
    }
  }

  getUserStatus(userId: number): UserStatusValue { // get status of a single user
    if (userId in this.usersStatus) { // if the id exists
      return this.usersStatus[userId];
    }
    return UserStatusValue.READY; // return READY if user doesn't exists
  }

  public get UserStatusValue(): typeof UserStatusValue { // we need to make the enum visible from the html
    return UserStatusValue; 
  }

  testIfVoting(): void { // test if the users are voting (or everybody done)
    if (this.users.length == 0) { // if no users
      this.isVotingEvent.emit(false);
      return;
    }
    for (const user of this.users) {
      const userStatus = this.getUserStatus(user.id);
      if (userStatus == UserStatusValue.VOTING || userStatus == UserStatusValue.DONE) {
        this.isVotingEvent.emit(true); // users are voting or done
        return;
      }
    }
    this.isVotingEvent.emit(false);
  }
}
