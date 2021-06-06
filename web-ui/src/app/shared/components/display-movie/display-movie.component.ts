import { Component, OnInit, OnChanges, Input } from '@angular/core'
import { MovieService } from '../../../services/movie.service'
import { Movie } from '../../interfaces/movie'

@Component({
  selector: 'app-display-movie',
  templateUrl: './display-movie.component.html',
  styleUrls: ['./display-movie.component.css']
})
export class DisplayMovieComponent implements OnInit {

  @Input() movieId: number = 0; // movie ID is passed as an input to the component

  movie? : Movie;

  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    this.getMovie(this.movieId);
  }

  ngOnChanges() { // "reload" the movie on change
    this.getMovie(this.movieId);
    // console.log(this.movieId);
  }

  getMovie(movieId: number): void {
    // subscribe to get the movie: async fct
    this.movieService.getMovie(movieId)
        .subscribe(movie => this.movie = movie);
  }
}
