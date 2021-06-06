import { Component, OnInit } from '@angular/core';
import { DisplayMovieComponent } from '../../../shared/components/display-movie/display-movie.component'
import { GroupService } from '../../../services/group.service'
import { ActivatedRoute, Router } from '@angular/router';

const maxSec = 22; // max number of seconds for the timer
@Component({
  selector: 'app-group-find-match',
  templateUrl: './group-find-match.component.html',
  styleUrls: ['./group-find-match.component.css']
})

export class GroupFindMatchComponent implements OnInit {

  timer?: NodeJS.Timeout;

  timerValue = this.microsecToTimerString(maxSec * 1000 - 1);

  movieId?: number;

  moviesSuggestions: number[] = []; // list of movies

  movieIndex: number = 0;

  constructor(private displayComponent: DisplayMovieComponent, private groupService: GroupService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    this.getMoviesSuggestions(() => {
      this.getMovieId(); // get the movie to watch
      this.startTimer(maxSec);
    });
  }

  startTimer(maxSec: number = 20,  then: () => any = () => {this.onMaybe();}) { // if the timer ends, send "maybe" as an answer
    // kill the timer if it already exists:
    if (this.timer !== undefined) {
      clearInterval(this.timer);
    }
    // this.timerValue = this.microsecToTimerString(maxSec * 1000);
    let goal = new Date().getTime() + maxSec*1000;
    this.timer = setInterval(() => {
      let now = new Date().getTime();
      let diff = goal - now;
      if (document.getElementById('timerValue')) {
        
      }
      if(document.getElementById('timerValue')) {
        if (diff >= 0) {
          // update the timer:
          this.timerValue = this.microsecToTimerString(diff);
          document.getElementById('timerValue')!.innerHTML = this.timerValue;
          // console.log(this.timerValue)
    
        } else {
          this.timerValue = "00:00";
          document.getElementById('timerValue')!.innerHTML = this.timerValue;
          // delete the timer if we are at the end of the time:
          clearInterval(this.timer!); // clear the timer
          this.timer = undefined;
          // call then fct:
          then();
        }
      } else {
        clearInterval(this.timer!);
      }
    }, 1000);
  }

  microsecToTimerString(sec: number): string { // convert micro seconds to string in the form min:sec
    let minutes_str: string = Math.floor(sec / 60000).toString()
    let seconds_str: string = Math.floor((sec / 1000) % 60).toString();

    if (minutes_str.length < 2) // add a zero
      minutes_str = "0" + minutes_str;
    
    if (seconds_str.length < 2) // add a zero
    seconds_str = "0" + seconds_str;
    
    return minutes_str + ':' + seconds_str;
  }

  getMovieId() {
    if (this.moviesSuggestions != undefined && this.moviesSuggestions.length > this.movieIndex) {
      this.movieId = this.moviesSuggestions[this.movieIndex];
    }
  }

  getMoviesSuggestions(then: () => any = () => void 0, onError: () => any = () => void 0) {
    this.groupService.getMoviesSuggestions()
      .subscribe(moviesSuggestions => {
        try {
          this.moviesSuggestions = moviesSuggestions;
          then();
        } catch (error) {
          onError();
        }
      });
  }

  getNextMovie() {
    if (this.movieId != undefined) {
      if (++this.movieIndex >= this.moviesSuggestions.length) { // test the lenght of the suggestion
        // go to the next page:
        this.goToWaitVoting();
      } else { // select next movie
        this.getMovieId();
      }
    }
  }

  goToWaitVoting() { // go to the wait voting page
    this.router.navigate(['wait-voting'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  onYes() {
    // console.log("Yes");
    this.timerValue = this.microsecToTimerString(maxSec * 1000 - 1);
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    //TODO: send response to backend and get new movie to watch
    this.getNextMovie();
    this.startTimer(maxSec);
  }

  onNo() {
    // console.log("No");
    this.timerValue = this.microsecToTimerString(maxSec * 1000 - 1);
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    //TODO: send response to backend and get new movie to watch
    this.getNextMovie();
    this.startTimer(maxSec);
  }

  onMaybe() {
    // console.log("Maybe");
    this.timerValue = this.microsecToTimerString(maxSec * 1000 - 1);
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    //TODO: send response to backend and get new movie to watch
    this.getNextMovie();
    this.startTimer(maxSec);
  }

}
