import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClientData } from './client.data.model';
import { environment as env } from 'src/environments/environment';
import { PagedResponse } from '../common/paged-response.model';

@Injectable({
  providedIn: 'root'
})
export class ClientDataService {
  private apiUrl = env.API_ROOT + 'v1/api/client'; // Replace with your API URL

  constructor(private http: HttpClient) {}

  getClients<T>(page: number, size: number): Observable<PagedResponse<T>> {
    //return this.http.get(`${this.apiUrl}?page=${page}&size=${size}`);
    return this.http.get<PagedResponse<T>>(`${this.apiUrl}`);
  }

  getClientById(id: string): Observable<ClientData> {
    return this.http.get<ClientData>(`${this.apiUrl}/${id}`);
  }

  createClient(client: ClientData): Observable<ClientData> {
    return this.http.post<ClientData>(this.apiUrl, client);
  }

  updateClient(id: string, client: ClientData): Observable<ClientData> {
    return this.http.put<ClientData>(`${this.apiUrl}/${id}`, client);
  }

  deleteClient(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
