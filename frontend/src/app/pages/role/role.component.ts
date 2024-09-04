import { ActivatedRoute, NavigationEnd, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';
import { MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatMenuModule } from '@angular/material/menu';
import { SelectionModel } from '@angular/cdk/collections';
import { RoleDataService } from 'src/app/core/role/role.data.service';
import { environment as env } from 'src/environments/environment';
import { RoleData } from 'src/app/core/role/role.data.model';
import { TableHeightDirective } from '../../shared/directives/table-height-directive';
import { MatDividerModule } from '@angular/material/divider';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrl: './role.component.scss',
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatSortModule, MatCheckboxModule,
    MatDividerModule, TableHeightDirective, MatIconModule, MatMenuModule,
    MatButtonModule, RouterModule, CommonModule,
  ],
})
export class RoleComponent implements OnInit, AfterViewInit {
  dateFormat: string = env.dateFormat;
  displayedColumns: string[] = ['id', 'name', 'createdAt', 'action',];
  selection = new SelectionModel<RoleData>(true, []);
  dataSource = new MatTableDataSource<any>([]);
  totalItems = 0;
  pageSize = env.pageSize;
  hasChildRoute = false;

  showRoleSecret: { [key: number]: boolean } = {};

  private paginator: MatPaginator;
  private sort: MatSort;

  @ViewChild('MatSort') set setSort(content: MatSort) {
    if (content) { // initially setter gets called with undefined
      this.sort = content;
      this.dataSource.sort = this.sort;
      this.paginator.page.subscribe(() => this.loadPage());
      this.loadPage();
    }
  }

  @ViewChild('paginator') set setPaginator(content: MatPaginator) {
    if (content) { // initially setter gets called with undefined
      this.paginator = content;
      this.dataSource.paginator = content;
      this.paginator.page.subscribe(() => this.loadPage());
      this.loadPage();
    }
  }

  constructor(
    private dataService: RoleDataService,
    private router: Router, private route: ActivatedRoute
  ) { }
  ngAfterViewInit(): void {
    window.dispatchEvent(new Event('resize'));
  }
  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.hasChildRoute = this.route.children.length > 0;
    });
    this.hasChildRoute = this.route.children.length > 0;
  }

  loadPage() {
    const pageIndex = this.paginator.pageIndex;
    const pageSize = this.paginator.pageSize;

    this.dataService
      .getRoles(pageIndex, pageSize)
      .subscribe((data) => {
        /*const row = data.items[0];
        for (let i = 0; i < 15; ++i) {
          let obj2 = JSON.parse(JSON.stringify(row));
          obj2.id = i + 1;
          data.items.push(obj2);
        }*/
        this.dataSource.data = data.items;
        this.totalItems = data.page.totalElements;
      });
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach((row) => this.selection.select(row));
  }

  editElement(id: string) {
    this.router.navigate(['edit', id], { relativeTo: this.route });
    console.log('Edit:', id);
  }

  deleteElement(id: string) {
    this.dataService.deleteRole(id).subscribe(() => { this.loadPage(); });
    // Delete logic here
    console.log('Delete:', id);
  }

  onCreateNew() {
    this.router.navigate(['create'], { relativeTo: this.route }); // Navigate to the home
  }

  toggleRoleSecretVisibility(clientId: number): void {
    this.showRoleSecret[clientId] = !this.showRoleSecret[clientId];
  }
}
