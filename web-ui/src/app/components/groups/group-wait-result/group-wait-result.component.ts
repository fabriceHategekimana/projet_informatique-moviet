import { Component, OnInit, OnDestroy } from '@angular/core';
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group';
import { UserService } from '../../../services/user.service';
import { GroupService } from 'src/app/services/group.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-group-wait-result',
  templateUrl: './group-wait-result.component.html',
  styleUrls: ['./group-wait-result.component.css']
})
export class GroupWaitResultComponent implements OnInit, OnDestroy {

  currentGroup?: Group;

  matchMovieId?: number;

  private timerRefreshResult? :NodeJS.Timeout;

  constructor(private groupsComponent : GroupsComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    let thenGroupimport = () => {
      this.matchMovieId = 0; //! MOCK
      this.currentGroup = this.groupsComponent.currentGroup; // save the current group
      this.getMovieResult();
    };

    this.groupsComponent.getGroup(undefined, thenGroupimport);

    // refresh result x micro-seconds:
    this.timerRefreshResult = setInterval(() => {this.getMovieResult();}, 1000);
  }

  ngOnDestroy() {
    if (this.timerRefreshResult != undefined) {
      clearInterval(this.timerRefreshResult); 
    }
  }
  
  goToShowResult() { // go to the show-result page
    history.pushState({ winningMovie: this.matchMovieId}, '', undefined); // push data to history
    this.router.navigate(['show-result'], {relativeTo: this.route.parent, skipLocationChange: true}); // pass the movieId to show-result
  }

  getMovieResult(then: () => any = () => {this.goToShowResult(); this.clearTimer();}) {
    if (this.currentGroup != undefined) {
      this.groupService.getMovieResult(this.currentGroup.id)
        .subscribe(movieId => {
          this.matchMovieId = movieId;
          then();
      }); 
    }
  }

  clearTimer() {
    if(this.timerRefreshResult!=undefined) clearInterval(this.timerRefreshResult);
  }
}
