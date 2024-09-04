import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestAclData } from './rest-acl.data.model'; // Update the model import
import { environment as env } from 'src/environments/environment';
import { PaginatedResponse } from '../common/paginated-response.model';

@Injectable({
  providedIn: 'root'
})
export class RestAclDataService {
  private apiUrl = env.API_ROOT + 'v1/api/restAcl'; // Update the API URL

  constructor(private http: HttpClient) { }

  getRestAcls(pageIndex: number, pageSize: number, sortDirection: string, sortField: string): Observable<PaginatedResponse<RestAclData>> {
    let params = new HttpParams()
      .set('pageIndex', pageIndex.toString())
      .set('pageSize', pageSize.toString())
      .set('sortDirection', sortDirection)
      .set('sortField', sortField);
    return this.http.get<PaginatedResponse<RestAclData>>(this.apiUrl, { params });
  }

  getRestAclById(id: string): Observable<RestAclData> {
    return this.http.get<RestAclData>(`${this.apiUrl}/${id}`);
  }

  createRestAcl(restAcl: RestAclData): Observable<RestAclData> {
    return this.http.post<RestAclData>(this.apiUrl, restAcl);
  }

  updateRestAcl(restAcl: RestAclData): Observable<RestAclData> {
    return this.http.put<RestAclData>(`${this.apiUrl}/${restAcl.id}`, restAcl);
  }

  deleteRestAcl(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
