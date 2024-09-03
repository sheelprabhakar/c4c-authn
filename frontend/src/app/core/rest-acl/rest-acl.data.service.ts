import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestAclData } from './rest-acl.data.model'; // Update the model import
import { environment as env } from 'src/environments/environment';
import { PagedResponse } from '../common/paged-response.model';

@Injectable({
  providedIn: 'root'
})
export class RestAclDataService {
  private apiUrl = env.API_ROOT + 'v1/api/restAcl'; // Update the API URL

  constructor(private http: HttpClient) { }

  getRestAcls(pageIndex: number, pageSize: number): Observable<PagedResponse<RestAclData[]>> {
    let params = new HttpParams()
      .set('pageIndex', pageIndex.toString())
      .set('pageSize', pageSize.toString());
    return this.http.get<PagedResponse<RestAclData[]>>(this.apiUrl, { params });
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
