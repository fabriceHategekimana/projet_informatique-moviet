import { Injectable } from '@angular/core';
import { Movie } from '../shared/interfaces/movie'; // import the movie interface
import { Observable, of } from 'rxjs'; // Observable => HTTP methods return Observable objects

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor() { }

  getMovie(): Observable<Movie> {
    // return mock
    return of({
      id: 3,
      name: "Les Affranchis",
      year: "1990",
      poster: "https://www.themoviedb.org/t/p/w220_and_h330_face/v4c6WMVqUlSJHMyjHNj72iTFGhm.jpg",
      backdrop: "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/sw7mordbZxgITU877yTpZCud90M.jpg",
      rating: 85});
  }
}
