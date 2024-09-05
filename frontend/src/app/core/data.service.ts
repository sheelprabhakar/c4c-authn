import { HttpParams } from "@angular/common/http";

export abstract class DataService<T>{
  protected params(pageIndex: number, pageSize: number, sortDirection: string, sortField: string) {
    return new HttpParams()
      .set('pageIndex', pageIndex.toString())
      .set('pageSize', pageSize.toString())
      .set('sortDirection', sortDirection)
      .set('sortField', sortField);
  }
}
