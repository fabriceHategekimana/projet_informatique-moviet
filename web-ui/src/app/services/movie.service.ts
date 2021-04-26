import { Injectable } from '@angular/core';
import { Movie } from '../shared/interfaces/movie'; // import the movie interface
import { Observable, of } from 'rxjs'; // Observable => HTTP methods return Observable objects
import { HttpClient, HttpHeaders } from '@angular/common/http'; // http requests
import { catchError, map, tap } from 'rxjs/operators'; // error handling

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  // http options for the request
  private httpOptions = {
    headers: new HttpHeaders({

    })};


  private movieUrl : string = "http://localhost:10081/movies/0";

  constructor(private http: HttpClient) { }

  getMovie(): Observable<any> {
    // console.log(this.http.get<Movie>(movieUrl, httpOptions));
    return this.http.get<Movie>(this.movieUrl, this.httpOptions)
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
