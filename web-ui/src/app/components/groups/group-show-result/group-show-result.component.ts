import { Component, OnInit } from '@angular/core';
import { Group } from '../../../shared/interfaces/group';
import { GroupService } from 'src/app/services/group.service';
import { GroupsComponent } from '../groups.component'
import { UsersStatus } from '../../../shared/interfaces/users-status'
import { UserStatusValue } from '../../../shared/interfaces/users-status'
import { UserService } from '../../../services/user.service';
import { User } from 'src/app/shared/interfaces/user';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-group-show-result',
  templateUrl: './group-show-result.component.html',
  styleUrls: ['./group-show-result.component.css']
})
export class GroupShowResultComponent implements OnInit {

  movieId?: number;

  currentGroup?: Group;

  myUser?: User;

  isAdmin: boolean = false;

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    let thenGroupimport = () => { // method to call after group import
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
      this.getMyUser(() => {
        this.testIfAdmin();
      });
    };
    this.groupsComponent.getGroup(undefined, thenGroupimport);
    this.getMovieId();
    if (this.movieId == undefined) {
      this.goToWaitResult(); // return to the waiting page if the movie does not exist
    }
  }

  goToWaitResult() { // go to the show-result page
    this.router.navigate(['wait-result'], {relativeTo: this.route.parent, skipLocationChange: true}); // pass the movieId to show-result
  }

  getMovieId(): void {
    if (history.state != undefined) {
      if (history.state.winningMovie != undefined) {
        this.movieId = history.state.winningMovie;
      }
    }
  }

  restart(): void {
    if (this.isAdmin) {
      if (this.currentGroup != undefined) {
        this.groupService.setUserStatus(this.currentGroup.id, this.getMyUserId(), UserStatusValue.CHOOSING)
          .subscribe(()=>{
            this.goToGroupInfo(); // go to the parent component page = group
          }); // reset to choosing
      } 
    }
  }

  goToGroupInfo(): void {
    // history.pushState()
    this.router.navigate(['group-info'], { relativeTo: this.route.parent, skipLocationChange: true});
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
}
