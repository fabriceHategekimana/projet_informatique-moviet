import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { GroupService } from 'src/app/services/group.service';
import { Group } from '../../shared/interfaces/group';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.css']
})
export class GroupsComponent implements OnInit {
  currentGroup?: Group;

  constructor(private route: ActivatedRoute, private groupService: GroupService, private router: Router) { } // route contain the current path

  ngOnInit(): void {
    // this.getGroup();
  }

  // the default "then" function is empty and the default "onError" = error 404 if groupId does not exist
  getGroup(id : number | string | undefined | null = undefined, then: () => any = () => void 0, onError: () => any = () => {this.router.navigate(['**'], { skipLocationChange: true });}): void {
    if (id == undefined)
      id = this.route.snapshot.paramMap.get('groupId'); // get the group ID
    if (id != null && id != undefined) {
      // get the group object:
      this.groupService.getGroup(Number(id))
        .subscribe(currentGroup => {
          this.currentGroup = currentGroup;
          if (this.currentGroup) { // if the group exist
            then();
          } else {
            onError();
          }
        });
    }
  }

  createGroup(then: () => any = () => void 0, onError: () => any = () => void 0): void {
    let groupID = 0
    // get the group object:
    this.groupService.createGroup()
      .subscribe(data => {
        // groupID = data.id;
        // TODO: create group and catch error
        try {
          groupID = Number(data.headers.get('Location').split(/.*[\/|\\]/)[1]);
          this.getGroup(groupID, then, onError);
        } catch (error) {
          onError();
        }
      });
  }

}
