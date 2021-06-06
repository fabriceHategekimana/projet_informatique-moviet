import { Injectable } from '@angular/core';
import { Movie } from '../shared/interfaces/movie' // import the movie interface
import { Genre } from '../shared/interfaces/genre' // import the genre interface
import { Keyword, KeywordResults } from '../shared/interfaces/keyword' // import keyword interface
import { Tag, Tags } from '../shared/interfaces/tags'
import { Observable, of } from 'rxjs'; // Observable => HTTP methods return Observable objects
import { HttpClient, HttpHeaders } from '@angular/common/http' // http requests
import { catchError, map, tap } from 'rxjs/operators' // error handling
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  // http options for the request
  private httpOptions = {
    headers: new HttpHeaders({

    })};


  private movieUrl : string = environment.API_URL + "movie-service/Mock_movies"; //! MOCK
  private genresUrl : string = environment.API_URL + "movie-service/Mock_movies/genres"; //! MOCK
  private keywordUrl : string = environment.API_URL + "movie-service/Mock_search/keyword"; //! MOCK

  constructor(private http: HttpClient) { }

  getMovie(movieId: number): Observable<any> { // type any because get can return httpEvent or Observable<Movie>
    // console.log(this.http.get<Movie>(movieUrl, httpOptions));
    return this.http.get<Movie>(this.movieUrl + '/' + movieId, this.httpOptions)
                  .pipe(catchError(this.handleError<Movie>('getMovie', undefined)));
    // return mock
    // return of({
    //   id: 3,
    //   title: "Les Affranchis",
    //   release_year: "1990",
    //   poster_url: "https://www.themoviedb.org/t/p/w220_and_h330_face/v4c6WMVqUlSJHMyjHNj72iTFGhm.jpg",
    //   backdrop_url: "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/sw7mordbZxgITU877yTpZCud90M.jpg",
    //   score: 8.5,
    //   genres: ["Action"]});
  }

  getTags(): Observable<any> { // type any because get can return httpEvent or Observable<Tags>
    //! return Mock
    return of({
      tags: [
        // {name: "years", values: ["1990", "2000", "2010"]},
        {name: "genre", values: ["Adventure", "Action", "Thriller", "Romance"]},
        {name: "OtherTag", values: ["value1", "value2", "value3"]}
      ]
    })
  }

  getGenres(): Observable<any> { // type any because get can return httpEvent or Observable<Genre[]>
    //! return Mock
    return this.http.get<Genre[]>(this.genresUrl, this.httpOptions)
                  .pipe(catchError(this.handleError<Movie>('getGenres', undefined)));
  }

  getKeywords(input: string): Observable<any> { // type any because get can return httpEvent or Observable<Genre[]>
    //! return Mock
    if (input.length != 0) { // if input is empty
      input = '*';
    }
    return this.http.get<KeywordResults>(this.keywordUrl + '/' + input, this.httpOptions)
                  .pipe(catchError(this.handleError<Movie>('getKeywords', undefined)));
  }

  //** handle error function from https://angular.io/tutorial/toh-pt6
  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // print the error
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
