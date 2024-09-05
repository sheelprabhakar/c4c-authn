import { SelectionModel } from '@angular/cdk/collections';
import { environment as env } from 'src/environments/environment';
import { GenericDataSource } from '../core/generic-data-source';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
export abstract class AbstractListPageComponent<T> {
  dateFormat: string = env.dateFormat;
  selection = new SelectionModel<T>(true, []);
  dataSource: GenericDataSource<T>;
  totalItems = 0;
  pageSize = env.pageSize;
  hasChildRoute = false;

  protected paginator: MatPaginator;
  protected sort: MatSort;
  protected router: Router;
  protected route: ActivatedRoute;

  constructor(router: Router, route: ActivatedRoute, dataSource: GenericDataSource<T>) {
    this.router = router;
    this.route = route;
    this.dataSource = dataSource;
  }

  protected init() {
    this.dataSource.totalItems$.subscribe(total => this.totalItems = total);
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.hasChildRoute = this.route.children.length > 0;
    });
    this.hasChildRoute = this.route.children.length > 0;
  }

  loadPage() {
    if (this.paginator !== undefined && this.sort !== undefined) {
      const pageIndex = this.paginator.pageIndex;
      const pageSize = this.paginator.pageSize;
      const sortDirection = this.sort.direction;
      const sortField = this.sort.active === undefined ? 'name' : this.sort.active;
      this.selection.clear();
      this.dataSource.loadData(pageIndex, pageSize, sortDirection, sortField);
    }
  }

  onPageChange(event: any): void {
    this.loadPage();
  }

  onSortChange(sort: any): void {
    this.loadPage();
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach(row => this.selection.select(row));
  }


  editElement(id: string, rtName: string) {
    this.router.navigate([rtName, id], { relativeTo: this.route });
    console.log('Edit:', id);
  }

  onCreateNew(rtName: string) {
    this.router.navigate([rtName], { relativeTo: this.route });
  }

}
