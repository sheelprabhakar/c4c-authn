import { CollectionViewer, DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize } from 'rxjs/operators';
import { PaginatedResponse } from './common/paginated-response.model';

export abstract class GenericDataSource<T> implements DataSource<T> {
  private dataSubject = new BehaviorSubject<T[]>([]);
  private loadingSubject = new BehaviorSubject<boolean>(false);
  private totalItemsSubject = new BehaviorSubject<number>(0);

  public loading$ = this.loadingSubject.asObservable();
  public totalItems$ = this.totalItemsSubject.asObservable();

  constructor() {
  }

  loadData(pageIndex: number, pageSize: number, sortDirection: string, sortField: string) {
    this.loadingSubject.next(true);
    this.fetchData(pageIndex, pageSize, sortDirection, sortField).pipe(
      catchError(() => of({ items: [], total: 0, page: { size: 0, number: 0, totalElements: 0, totalPages: 0 } })),
      finalize(() => this.loadingSubject.next(false))
    ).subscribe(data => {
      this.dataSubject.next(data.items);
      this.totalItemsSubject.next(data.page.totalElements);
    });
  }

  connect(collectionViewer: CollectionViewer): Observable<T[]> {
    return this.dataSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.dataSubject.complete();
    this.loadingSubject.complete();
    this.totalItemsSubject.complete();
  }

  get data(): T[] {
    return this.dataSubject.value;
  }

  protected abstract fetchData(pageIndex: number, pageSize: number, sortDirection: string, sortField: string): Observable<PaginatedResponse<T>>;
}
