import { Component, Input, OnInit, Output, EventEmitter, OnChanges } from '@angular/core';
import { GroupsComponent } from '../../../components/groups/groups.component'
import { Group } from '../../../shared/interfaces/group';
import { User } from '../../../shared/interfaces/user'
import { UserService } from '../../../services/user.service';
import { GroupService } from '../../../../app/services/group.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersStatus } from '../../../shared/interfaces/users-status';
import { UserStatusValue } from '../../../shared/interfaces/users-status';

@Component({
  selector: 'app-display-users-status',
  templateUrl: './display-users-status.component.html',
  styleUrls: ['./display-users-status.component.css']
})
export class DisplayUsersStatusComponent implements OnInit, OnChanges {

  @Input() currentGroup?: Group; // currentGroup as an input parameter of the child

  @Output() groupStatusEvent = new EventEmitter<UserStatusValue>(); // indicate that status of the group

  users: User[] = [];
  usersStatus : UsersStatus = {}; // map user id and status
  groupStatus? : UserStatusValue;

  constructor(private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
      // import all users + import all user status + test if all users have voted
      this.getAllUsers(() => {this.getUsersStatus(); this.getGroupStatus();});
  }

  ngOnChanges(): void { // when the input changes
      // import all users + import all user status + test if all users have voted
      this.getAllUsers(() => {this.getUsersStatus(); this.getGroupStatus();});
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
  getAllUsers(then: () => any = () => void 0) {
    if (this.currentGroup) {
      const nbUsers = this.currentGroup.users.length;
      this.currentGroup.users.forEach((groupUser, i) => {
        const userId = groupUser.id;
        if (i == nbUsers - 1) {
          this.getUser(userId, then(), then());          
        } else {
          this.getUser(userId);
        }
        // TODO: remove user if not in the list anymore
      });
    }
  }


  getUsersStatus(then: () => any = () => void 0) {
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
        then();
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
    return UserStatusValue.CHOOSING; // return CHOOSING as the default value
  }

  public get UserStatusValue(): typeof UserStatusValue { // we need to make the enum visible from the html
    return UserStatusValue; 
  }

  getGroupStatus(then: () => any = () => {this.emitGroupStatus();}): void {
    if (this.currentGroup != undefined) {
      this.groupService.getGroupStatus(this.currentGroup.id)
      .subscribe((status) => {
        this.groupStatus = status;
        then();
      });
    }
  }

  emitGroupStatus(): void { // send the group status
    if (this.groupStatus != undefined) {
      this.groupStatusEvent.emit(this.groupStatus);
    }
  }
}
