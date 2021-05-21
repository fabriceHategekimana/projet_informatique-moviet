import { Component, OnInit } from '@angular/core';
import { DisplayMovieComponent } from '../../../shared/components/display-movie/display-movie.component'

@Component({
  selector: 'app-group-find-match',
  templateUrl: './group-find-match.component.html',
  styleUrls: ['./group-find-match.component.css']
})
export class GroupFindMatchComponent implements OnInit {

  timerValue = "";

  constructor(private displayComponent: DisplayMovieComponent) { }

  ngOnInit(): void {
    this.startTimer(62);
  }

  startTimer(maxSec: number = 20) {
    let goal = new Date().getTime() + maxSec*1000;
    let timer = setInterval(() => {
      let now = new Date().getTime();
      let diff = goal - now;

      if (diff >= 0) {
        let minutes_str: string = Math.floor(diff / 60000).toString()
        let seconds_str: string = Math.floor((diff / 1000) % 60).toString();
  
        if (minutes_str.length < 2) // add a zero
          minutes_str = "0" + minutes_str;
        
        if (seconds_str.length < 2) // add a zero
        seconds_str = "0" + seconds_str;
        
        // update the timer:
        this.timerValue = minutes_str + ":" + seconds_str;
        // console.log(this.timerValue)
  
      } else {
        this.timerValue = "00:00";
        // delete the timer if we are at the end of the time:
        clearInterval(timer);
      }
    }, 1000);
  }

}
