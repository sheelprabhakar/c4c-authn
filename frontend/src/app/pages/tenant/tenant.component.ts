import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
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
import { TenantDataService } from 'src/app/core/tenant/tenant.data.service';
import { environment as env } from 'src/environments/environment';
import { TenantData } from 'src/app/core/tenant/tenant.data.model';
import { TableHeightDirective } from '../../shared/directives/table-height-directive';
import { MatDividerModule } from '@angular/material/divider';
@Component({
  selector: 'app-tenant',
  templateUrl: './tenant.component.html',
  styleUrl: './tenant.component.scss',
  standalone: true,
  imports: [
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCheckboxModule,
    MatDividerModule,
    TableHeightDirective,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    RouterModule,
    CommonModule,
  ],
})
export class TenantComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = [
    'id',
    'name',
    'email',
    /*'address',
    'cityId',
    'pin',
    'phone',
    'mobile',*/
    'shortName',
    'updatedAt',
    'active',
    'action',
  ];
  selection = new SelectionModel<TenantData>(true, []);
  dataSource = new MatTableDataSource<any>([]);
  totalItems = 0;
  pageSize = env.pageSize;
  hasChildRoute = false;

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
    private dataService: TenantDataService,
    private el: ElementRef,
    private renderer: Renderer2,
    private router: Router,
    private route: ActivatedRoute
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
      .getData<TenantData>(pageIndex, pageSize)
      .subscribe((data) => {
        const row = data.items[0];
        for (let i = 0; i < 15; ++i) {
          let obj2 = JSON.parse(JSON.stringify(row));
          data.items.push(obj2);
        }
        data.total = 16;
        this.dataSource.data = data.items;
        this.totalItems = data.total;
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

  editElement(element: any) {
    // Edit logic here
    console.log('Edit:', element);
  }

  deleteElement(element: any) {
    // Delete logic here
    console.log('Delete:', element);
  }

  onCreateNew() {
    this.router.navigate(['create'], { relativeTo: this.route }); // Navigate to the home
  }
}
