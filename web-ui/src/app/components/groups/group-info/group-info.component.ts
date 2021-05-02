import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-group-info',
  templateUrl: './group-info.component.html',
  styleUrls: ['./group-info.component.css']
})
export class GroupInfoComponent implements OnInit {

  currentGroup?: Group;

  constructor(private groupsComponent : GroupsComponent, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.groupsComponent.getGroup(
      undefined,
      () => {
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        // console.log(this.currentGroup);
      }
    )
  }

  goToGenres() { // go to the genres selection page
    this.router.navigate(['genres'], {relativeTo: this.route, skipLocationChange: true });
  }

}
