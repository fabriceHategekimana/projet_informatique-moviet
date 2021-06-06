import { Injectable } from '@angular/core'
import { Observable, of } from 'rxjs' // Observable => HTTP methods return Observable objects
import { HttpClient, HttpHeaders } from '@angular/common/http' // http requests
import { catchError, map, tap } from 'rxjs/operators' // error handling
import { Group } from '../shared/interfaces/group'
import { UsersStatus } from '../shared/interfaces/users-status'
import { UserStatusValue } from '../shared/interfaces/users-status'
import { MoviePreferences } from '../shared/interfaces/movie-preferences'
import { group } from 'node:console'
import { environment } from '../../environments/environment';

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

  private httpOptionsPut = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  private groupsUrl : string = environment.API_URL +  "groups"; // url using api
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

  sendPreferences(groupId: number, moviePreferences: MoviePreferences): Observable<any> { // send the user preferences
    //! MOCK
    //TODO: send preferences
    return of();
  }

  getMoviesSuggestions(): Observable<number[]> {
    //! MOCK
    return of([1, 2, 3, 4]);
  }

  getMovieResult(groupId: number): Observable<number> {
    //! MOCK
    return of(123);
  }

  getUserStatus(groupId: number, userId: number): Observable<any> {
    // console.log(this.groupsUrl + "/" + groupId + "/" + "users/" + userId + "/status");
    return this.http.get<UserStatusValue>(this.groupsUrl + "/" + groupId + "/" + "users/" + userId + "/status", this.httpOptionsGet)
                  .pipe(catchError(this.handleError<any>('getUserStatus', undefined)));
  }

  setUserStatus(groupId: number, userId: number, status: UserStatusValue): Observable<any> {
    // console.log(this.groupsUrl + "/" + groupId + "/" + "users/" + userId + "/status");
    return this.http.put<UserStatusValue>(this.groupsUrl + "/" + groupId + "/" + "users/" + userId + "/status", status, this.httpOptionsPut)
                  .pipe(catchError(this.handleError<any>('setUserStatus', undefined)));
  }

  setGroupStatus(groupId: number, status: UserStatusValue): Observable<any> {
    return this.http.put<UserStatusValue>(this.groupsUrl + "/" + groupId + "/users_status", status, this.httpOptionsPut)
                  .pipe(catchError(this.handleError<any>('setGroupStatus', undefined)));
  }

  getGroupStatus(id : number): Observable<any> { // type any because get can return httpEvent or Observable<UserStatusValue>
    return this.http.get<UserStatusValue>(this.groupsUrl + "/" + id + "/group_status", this.httpOptionsGet)
                  .pipe(catchError(this.handleError<UserStatusValue>('getGroupStatus', undefined)));
  }
}
