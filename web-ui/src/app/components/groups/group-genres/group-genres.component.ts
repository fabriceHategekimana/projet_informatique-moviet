import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';

@Component({
  selector: 'app-group-genres',
  templateUrl: './group-genres.component.html',
  styleUrls: ['./group-genres.component.css']
})
export class GroupGenresComponent implements OnInit {

  currentGroup?: Group;

  constructor(private groupsComponent : GroupsComponent) { }

  ngOnInit(): void {
    this.groupsComponent.getGroup(
      () => {
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        console.log(this.currentGroup);
      }
    )
  }

}
