// src/app/core/services/role.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RoleData } from './role.data.model';
import { environment as env } from 'src/environments/environment';
import { PagedResponse } from '../common/paged-response.model';

@Injectable({
  providedIn: 'root'
})
export class RoleDataService {
  private apiUrl = env.API_ROOT + 'v1/api/role'; // Replace with your API URL

  constructor(private http: HttpClient) { }

  getRoles(pageIndex: number, pageSize: number): Observable<PagedResponse<RoleData[]>> {
    let params = new HttpParams()
      .set('pageIndex', pageIndex.toString())
      .set('pageSize', pageSize.toString());
    return this.http.get<PagedResponse<RoleData[]>>(this.apiUrl, { params });
  }

  getRoleById(id: string): Observable<RoleData> {
    return this.http.get<RoleData>(`${this.apiUrl}/${id}`);
  }

  createRole(role: RoleData): Observable<RoleData> {
    return this.http.post<RoleData>(this.apiUrl, role);
  }

  updateRole(role: RoleData): Observable<RoleData> {
    return this.http.put<RoleData>(`${this.apiUrl}/${role.id}`, role);
  }

  deleteRole(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
