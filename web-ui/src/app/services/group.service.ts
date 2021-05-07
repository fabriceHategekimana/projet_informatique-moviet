import { Injectable } from '@angular/core'
import { Observable, of } from 'rxjs' // Observable => HTTP methods return Observable objects
import { HttpClient, HttpHeaders } from '@angular/common/http' // http requests
import { catchError, map, tap } from 'rxjs/operators' // error handling
import { Group } from '../shared/interfaces/group'

@Injectable({
  providedIn: 'root'
})
export class GroupService {
  // http options for the request
  private httpOptions = {
    headers: new HttpHeaders({

    })};


  private groupsUrl : string = "http://localhost/api/v1/groups"; // url using api

  constructor(private http: HttpClient) { }

  getGroup(id : number): Observable<any> { // type any because get can return httpEvent or Observable<Movie>
    return this.http.get<Group>(this.groupsUrl + "/" + id, this.httpOptions)
                  .pipe(catchError(this.handleError<Group>('getGroup', undefined)));
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
