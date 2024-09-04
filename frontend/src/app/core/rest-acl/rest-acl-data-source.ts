import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GenericDataSource } from '../generic-data-source';
import { RestAclData } from './rest-acl.data.model';
import { RestAclDataService } from './rest-acl.data.service';
import { PaginatedResponse } from '../common/paginated-response.model';

@Injectable()
export class RestAclDataSource extends GenericDataSource<RestAclData> {
  constructor(private dataService: RestAclDataService) {
    super();
  }

  protected fetchData(pageIndex: number, pageSize: number, sortDirection: string, sortField: string): Observable<PaginatedResponse<RestAclData>> {
    return this.dataService.getRestAcls(pageIndex, pageSize, sortDirection, sortField);
  }
}
