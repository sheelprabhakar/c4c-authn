import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment as env } from 'src/environments/environment';
import { PagedResponse } from '../common/paged-response.model';
import { UserDetailsData } from './user.data.model';
import { constants } from '../common/constants';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  private apiUrl = env.API_ROOT + 'v1/api/user'; // Replace with your API URL

  constructor(private http: HttpClient) {}

  getData<T>(page: number, size: number): Observable<PagedResponse<T>> {
    //return this.http.get(`${this.apiUrl}?page=${page}&size=${size}`);
    return this.http.get<PagedResponse<T>>(`${this.apiUrl}`);
  }

  getDetail(): Observable<any> {
    return this.http.get<UserDetailsData>(`${this.apiUrl}/me`).pipe(
      tap((response) => {
        console.log("Setting user details")
        localStorage.setItem(constants.USER_DETAILS, JSON.stringify(response));
      })
    );
  }
}
