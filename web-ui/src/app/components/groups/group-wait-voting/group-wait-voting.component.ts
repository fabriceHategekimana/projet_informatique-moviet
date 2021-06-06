import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { UserService } from '../../../services/user.service';
import { GroupService } from 'src/app/services/group.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-group-wait-voting',
  templateUrl: './group-wait-voting.component.html',
  styleUrls: ['./group-wait-voting.component.css']
})
export class GroupWaitVotingComponent implements OnInit {

  currentGroup?: Group;

  private timerRefreshResult? :NodeJS.Timeout;

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    let thenGroupimport = () => {
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
    };

    this.groupsComponent.getGroup(undefined, thenGroupimport);

    // refresh group info every x micro-seconds:
    this.timerRefreshResult = setInterval(() => {this.groupsComponent.getGroup(undefined, thenGroupimport);}, 1000);
  }

  goToWaitResult() { // go to the show-result page
    if (this.timerRefreshResult != undefined) {
      this.clearTimer();
    }
    this.router.navigate(['wait-result'], {relativeTo: this.route.parent, skipLocationChange: true}); // pass the movieId to show-result
  }

  endVoting() {
    //TODO: test if admin:
    this.goToWaitResult();
  }

  clearTimer() {
    if(this.timerRefreshResult!=undefined) clearInterval(this.timerRefreshResult);
  }
}
