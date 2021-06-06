import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { User } from '../../../shared/interfaces/user'
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

    this.groupsComponent.getGroup(undefined, thenGroupimport);

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

  goToFindMatchIfVoting(isVoting: any) { // go to the voting page if the users are voting
    // console.log(isVoting);
    this.clearTimer();
    if(isVoting) {
      this.goToFindMatch();
    }
  }

  clearTimer() {
    if(this.timerRefreshResult!=undefined) clearInterval(this.timerRefreshResult);
  }
}
