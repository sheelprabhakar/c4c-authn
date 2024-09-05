import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GenericDataSource } from '../generic-data-source';
import { ClientData } from './client.data.model';
import { ClientDataService } from './client.data.service';
import { PaginatedResponse } from '../common/paginated-response.model';

@Injectable()
export class ClientDataSource extends GenericDataSource<ClientData> {
  constructor(private dataService: ClientDataService) {
    super();
  }

  // Add your additional code here
  // This is where you can customize the behavior of the ClientDataSource class
  // You can add any additional methods or properties that you need
  // Remember to import any necessary dependencies
  // Make sure to return the appropriate data type in the fetchData method
  protected fetchData(pageIndex: number, pageSize: number, sortDirection: string, sortField: string): Observable<PaginatedResponse<ClientData>> {
    return this.dataService.findAll(pageIndex, pageSize, sortDirection, sortField);
  }
}
