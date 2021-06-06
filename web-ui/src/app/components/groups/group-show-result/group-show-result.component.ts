import { Component, OnInit } from '@angular/core';
import { Group } from '../../../shared/interfaces/group';
import { GroupService } from 'src/app/services/group.service';
import { GroupsComponent } from '../groups.component'
import { UsersStatus } from '../../../shared/interfaces/users-status'
import { UserStatusValue } from '../../../shared/interfaces/users-status'
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-group-show-result',
  templateUrl: './group-show-result.component.html',
  styleUrls: ['./group-show-result.component.css']
})
export class GroupShowResultComponent implements OnInit {

  movieId?: number;

  currentGroup?: Group;

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    let thenGroupimport = () => { // method to call after group import
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
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
    //TODO: check if admin
    //TODO: reset group status
    if (this.currentGroup != undefined) {
      this.groupService.setUserStatus(this.currentGroup.id, this.getMyUserId(), UserStatusValue.CHOOSING)
        .subscribe(()=>{
          this.goToGroupInfo(); // go to the parent component page = group
        }); // reset to choosing
    }
  }

  goToGroupInfo(): void {
    // history.pushState()
    this.router.navigate(['group-info'], { relativeTo: this.route.parent, skipLocationChange: true});
  }

  getMyUserId(): number { //! Temporary
    return 1;
  }
}
