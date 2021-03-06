import { Injectable } from '@angular/core'
import { Observable, of } from 'rxjs' // Observable => HTTP methods return Observable objects
import { HttpClient, HttpHeaders } from '@angular/common/http' // http requests
import { catchError, map, tap } from 'rxjs/operators' // error handling
import { User } from '../shared/interfaces/user'
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
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

  private usersUrl : string = environment.API_URL + "users"; // url using api
  
  public myUserInfo? : User;

  constructor(private http: HttpClient) { }

  getUser(id : string): Observable<any> { // type any because get can return httpEvent or Observable<User>
    console.log("getUser:", this.usersUrl + "/" + encodeURI(id));
    return this.http.get<User>(this.usersUrl + "/" + encodeURI(id), this.httpOptionsGet)
                  .pipe(catchError(this.handleError<User>('getUser', undefined)));
  }

  createUser(): Observable<any> { // create a user
    console.log("createUser:", this.usersUrl);
    let body = environment.production ? "" : "NewUser";
    return this.http.post<string>(this.usersUrl, body, this.httpOptionsPost)
                  .pipe(catchError(this.handleError<any>('createUser', undefined)));
  }

  whoAmI(): Observable<any> {
    console.log("whoAmI:", this.usersUrl + "/whoami");
    if (environment.production) {
      return this.http.get<User>(this.usersUrl + "/whoami", this.httpOptionsGet)
        .pipe(catchError(this.handleError<User>('whoAmI', undefined))); 
    } else {
      return of({username: "nom", id: "1"});
    }
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