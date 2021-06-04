import { Component, OnInit } from '@angular/core';
import { DisplayMovieComponent } from '../../../shared/components/display-movie/display-movie.component'

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

  constructor(private displayComponent: DisplayMovieComponent) { }

  ngOnInit(): void {
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    this.getMovieId(); // get the movie to watch
    this.startTimer(maxSec);
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

  getMovieId() { //! MOCK
    this.movieId = 0;
  }

  onYes() {
    console.log("Yes");
    this.timerValue = this.microsecToTimerString(maxSec * 1000 - 1);
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    //TODO: send response to backend and get new movie to watch
    this.getMovieId();
    this.startTimer(maxSec)
  }

  onNo() {
    console.log("Non");
    this.timerValue = this.microsecToTimerString(maxSec * 1000 - 1);
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    //TODO: send response to backend and get new movie to watch
    this.getMovieId();
    this.startTimer(maxSec)
  }

  onMaybe() {
    console.log("Maybe");
    this.timerValue = this.microsecToTimerString(maxSec * 1000 - 1);
    document.getElementById('timerValue')!.innerHTML = this.timerValue;
    //TODO: send response to backend and get new movie to watch
    this.getMovieId();
    this.startTimer(maxSec)
  }

}
