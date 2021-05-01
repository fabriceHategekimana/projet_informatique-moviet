import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';

@Component({
  selector: 'app-group-info',
  templateUrl: './group-info.component.html',
  styleUrls: ['./group-info.component.css']
})
export class GroupInfoComponent implements OnInit {

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
