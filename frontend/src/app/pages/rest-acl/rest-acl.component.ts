import { ActivatedRoute, NavigationEnd, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';
import { MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatMenuModule } from '@angular/material/menu';
import { SelectionModel } from '@angular/cdk/collections';
import { RestAclDataService } from 'src/app/core/rest-acl/rest-acl.data.service';
import { environment as env } from 'src/environments/environment';
import { RestAclData } from 'src/app/core/rest-acl/rest-acl.data.model';
import { TableHeightDirective } from '../../shared/directives/table-height-directive';
import { MatDividerModule } from '@angular/material/divider';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { RestAclDataSource } from 'src/app/core/rest-acl/rest-acl-data-source';

@Component({
  selector: 'app-rest-acl',
  templateUrl: './rest-acl.component.html',
  styleUrls: ['./rest-acl.component.scss'],
  standalone: true,
  providers: [RestAclDataSource],
  imports: [
    MatTableModule, MatPaginatorModule, MatSortModule, MatCheckboxModule,
    MatDividerModule, TableHeightDirective, MatIconModule, MatMenuModule,
    MatButtonModule, RouterModule, CommonModule,
  ],
})
export class RestAclComponent implements OnInit, AfterViewInit {
  dateFormat: string = env.dateFormat;
  displayedColumns: string[] = ['id', 'name', 'path', 'createdAt', 'action'];
  selection = new SelectionModel<RestAclData>(true, []);
  dataSource: RestAclDataSource;
  totalItems = 0;
  pageSize = env.pageSize;
  hasChildRoute = false;

  showRestAclSecret: { [key: number]: boolean } = {};

  private paginator: MatPaginator;
  private sort: MatSort;

  @ViewChild(MatSort, { static: false }) set setSort(content: MatSort) {
    if (content) { // initially setter gets called with undefined
      this.sort = content;
    }
  }

  @ViewChild('paginator') set setPaginator(content: MatPaginator) {
    if (content) { // initially setter gets called with undefined
      this.paginator = content;
    }
  }

  constructor(private dataService: RestAclDataService, private router: Router,
    private route: ActivatedRoute, private restAclDataSource: RestAclDataSource) {
    this.dataSource = this.restAclDataSource;
  }

  ngOnInit(): void {

    this.dataSource.totalItems$.subscribe(total => this.totalItems = total);

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.hasChildRoute = this.route.children.length > 0;
    });
    this.hasChildRoute = this.route.children.length > 0;
  }

  ngAfterViewInit(): void {
    this.dataSource.loadData(0, 10, 'asc', 'name'); // Example of loading data
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

  editElement(id: string) {
    this.router.navigate(['edit', id], { relativeTo: this.route });
    console.log('Edit:', id);
  }

  deleteElement(id: string) {
    this.dataService.deleteRestAcl(id).subscribe(() => { this.loadPage(); });
    console.log('Delete:', id);
  }

  onCreateNew() {
    this.router.navigate(['create'], { relativeTo: this.route });
  }

  toggleRestAclSecretVisibility(clientId: number): void {
    this.showRestAclSecret[clientId] = !this.showRestAclSecret[clientId];
  }
}
