import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClientData } from './client.data.model';
import { environment as env } from 'src/environments/environment';
import { PaginatedResponse } from '../common/paginated-response.model';
import { DataService } from '../data.service';

@Injectable({
  providedIn: 'root'
})
export class ClientDataService extends DataService<ClientData> {
  private apiUrl = env.API_ROOT + 'v1/api/client'; // Replace with your API URL

  constructor(private http: HttpClient) {
    super();
  }

  findAll(pageIndex: number, pageSize: number, sortDirection: string, sortField: string): Observable<PaginatedResponse<ClientData>> {
    let params = this.params(pageIndex, pageSize, sortDirection, sortField);
    return this.http.get<PaginatedResponse<ClientData>>(`${this.apiUrl}`, { params });
  }

  getById(id: string): Observable<ClientData> {
    return this.http.get<ClientData>(`${this.apiUrl}/${id}`);
  }

  createNew(client: ClientData): Observable<ClientData> {
    return this.http.post<ClientData>(`${this.apiUrl}?name=${client.name}`,{});
  }

  update(client: ClientData): Observable<ClientData> {
    return this.http.put<ClientData>(`${this.apiUrl}/${client.id}`, client);
  }

  deleteById(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
