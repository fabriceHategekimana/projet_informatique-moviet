import { Component, OnInit } from '@angular/core';
import { ɵINTERNAL_BROWSER_DYNAMIC_PLATFORM_PROVIDERS } from '@angular/platform-browser-dynamic';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-group-show-result',
  templateUrl: './group-show-result.component.html',
  styleUrls: ['./group-show-result.component.css']
})
export class GroupShowResultComponent implements OnInit {

  movieId?: number;

  constructor(private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
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
    //TODO: reset group status
    this.goToParent(); // go to the parent component page = group
  }

  goToParent(): void {
    this.router.navigate(['group-info'], { relativeTo: this.route.parent, skipLocationChange: true});
  }

}
