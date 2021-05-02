import { Component, OnInit } from '@angular/core'
import { MovieService } from '../../../services/movie.service'
import { Movie } from '../../interfaces/movie'

@Component({
  selector: 'app-display-movie',
  templateUrl: './display-movie.component.html',
  styleUrls: ['./display-movie.component.css']
})
export class DisplayMovieComponent implements OnInit {

  movie? : Movie;

  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    this.getMovie();
  }

  getMovie(): void {
    // subscribe to get the movie: async fct
    this.movieService.getMovie()
        .subscribe(movie => this.movie = movie);
  }
}
