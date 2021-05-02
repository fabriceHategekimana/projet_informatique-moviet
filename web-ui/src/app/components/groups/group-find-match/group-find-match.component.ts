import { Component, OnInit } from '@angular/core';
import { DisplayMovieComponent } from '../../../shared/components/display-movie/display-movie.component'

@Component({
  selector: 'app-group-find-match',
  templateUrl: './group-find-match.component.html',
  styleUrls: ['./group-find-match.component.css']
})
export class GroupFindMatchComponent implements OnInit {

  constructor(private displayComponent: DisplayMovieComponent) { }

  ngOnInit(): void {

  }



}
