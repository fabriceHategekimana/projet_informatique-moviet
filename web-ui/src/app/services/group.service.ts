import { Injectable } from '@angular/core'
import { Observable, of } from 'rxjs' // Observable => HTTP methods return Observable objects
import { HttpClient, HttpHeaders } from '@angular/common/http' // http requests
import { catchError, map, tap } from 'rxjs/operators' // error handling
import { Group } from '../shared/interfaces/group'
import { UsersStatus } from '../shared/interfaces/users-status'

@Injectable({
  providedIn: 'root'
})
export class GroupService {
  // http options for the request
  private httpOptionsGet = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  private httpOptionsPost = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
    observe: 'response' as 'response'
  };

  private groupsUrl : string = "http://localhost/api/v1/groups"; // url using api
  //private groupsUrl : string = "http://localhost:10080/groups";
  
  constructor(private http: HttpClient) { }

  getGroup(id : number): Observable<any> { // type any because get can return httpEvent or Observable<Group>
    return this.http.get<Group>(this.groupsUrl + "/" + id, this.httpOptionsGet)
                  .pipe(catchError(this.handleError<Group>('getGroup', undefined)));
  }

  createGroup(): Observable<any> {
    return this.http.post<Group>(this.groupsUrl, {name: 'newGroup'}, this.httpOptionsPost)
                  .pipe(catchError(this.handleError<Group>('createGroup', undefined)));
  }

  getUsersStatus(id : number): Observable<any> { // type any because get can return httpEvent or Observable<UsersStatus>
    return this.http.get<UsersStatus>(this.groupsUrl + "/" + id + "/users_status", this.httpOptionsGet)
                  .pipe(catchError(this.handleError<UsersStatus>('getUsersStatus', undefined)));
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
