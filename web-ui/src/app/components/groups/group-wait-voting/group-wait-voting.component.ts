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

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    let thenGroupimport = () => {
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
    };

    this.groupsComponent.getGroup(undefined, thenGroupimport);

    // refresh group info every x micro-seconds:
    setInterval(() => {this.groupsComponent.getGroup(undefined, thenGroupimport);}, 1000);
  }

  goToGenres() { // go to the genres selection page
    this.router.navigate(['genres'], {relativeTo: this.route, skipLocationChange: true });
  }

  goToFindMatch() { // go to the find-match page
    this.router.navigate(['find-match'], {relativeTo: this.route.parent, skipLocationChange: true });
  }
}
