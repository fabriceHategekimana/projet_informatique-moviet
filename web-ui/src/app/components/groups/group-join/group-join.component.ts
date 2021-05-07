import { Component, OnInit } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-group-join',
  templateUrl: './group-join.component.html',
  styleUrls: ['./group-join.component.css']
})
export class GroupJoinComponent implements OnInit {

  currentGroup?: Group;
  joinHidden: boolean = true; // hide the join button
  errorGroup: boolean = false; // if the group does not exist

  constructor(private groupsComponent : GroupsComponent, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {

  }

  displayJoin(): void { // display the button to join the group
    this.joinHidden = false;
  }

  displayErrorJoin(): void { // error if the group does not exist
    this.errorGroup = true;
  }

  joinGroup(id: string) {
    this.groupsComponent.getGroup(
      id,
      () => { // then
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        // join the group:
        this.router.navigate([id], { relativeTo: this.route });
      },
      () => { // if error
        console.log("Error: the group doesn't exist");
        this.displayErrorJoin();
      }      
    )
  }

  createGroup() {
    this.groupsComponent.createGroup(
      () => { // then
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        // join the group:
        console.log(this.groupsComponent.currentGroup);
        this.router.navigate([this.currentGroup!.id], { relativeTo: this.route });
      },
      () => { // if error
        console.log("Error creating group: the group doesn't exist");
      }      
    )
  }
}
