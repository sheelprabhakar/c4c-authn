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
import { environment as env } from 'src/environments/environment';
import { ClientDataService } from 'src/app/core/client/client.data.service';
import { ClientData } from 'src/app/core/client/client.data.model';
import { TableHeightDirective } from '../../shared/directives/table-height-directive';
import { MatDividerModule } from '@angular/material/divider';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { ClientDataSource } from 'src/app/core/client/client-data-source';
import { AbstractListPageComponent } from '../base.list-page.component';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.scss'],
  standalone: true,
  providers: [ClientDataSource],
  imports: [
    MatTableModule, MatPaginatorModule, MatSortModule, MatCheckboxModule,
    MatDividerModule, TableHeightDirective, MatIconModule, MatMenuModule,
    MatButtonModule, RouterModule, CommonModule,
  ],
})
export class ClientComponent extends AbstractListPageComponent<ClientData> implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'name', 'path', 'createdAt', 'action'];

  showClientSecret: { [key: number]: boolean } = {};

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

  constructor(private dataService: ClientDataService, private rtr: Router,
    private rt: ActivatedRoute, private restAclDataSource: ClientDataSource) {
    super(rtr, rt, restAclDataSource);
  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {
    this.dataSource.loadData(0, env.pageSize, 'asc', 'name'); // Example of loading data
  }

  toggleClientSecretVisibility(clientId: number): void {
    this.showClientSecret[clientId] = !this.showClientSecret[clientId];
  }

  deleteElement(id: string) {
    this.dataService.deleteById(id).subscribe(() => { this.loadPage(); });
    console.log('Delete:', id);
  }
}
