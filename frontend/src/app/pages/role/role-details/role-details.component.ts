import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { FormBuilder, FormGroup, FormsModule, Validators, ReactiveFormsModule } from '@angular/forms';
import { FormMode } from 'src/app/core/common/form.mode';
import { RoleData } from 'src/app/core/role/role.data.model';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleDataService } from 'src/app/core/role/role.data.service';
import { C4cListComponent } from '../../../shared/c4c-list/c4c-list.component';
export interface Item {
  id: number;
  name: string;
}
@Component({
  selector: 'app-role-details',
  templateUrl: './role-details.component.html',
  styleUrl: './role-details.component.scss',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule, MatIconModule,
    MatButtonModule,
    MatGridListModule, ReactiveFormsModule,
    C4cListComponent,
  ]
})
export class RoleDetailsComponent {

  role: RoleData = {
    name: '',
    tenantId: '',
    deleted: false,
    // Initialize other properties as needed
  };

  mode: FormMode;
  id: string;
  roleForm: FormGroup;
  errorMessage: string

  constructor(private route: ActivatedRoute, private router: Router,
    private dataService: RoleDataService,
    private fb: FormBuilder,
  ) {
    this.roleForm = this.fb.group({
      name: ['', Validators.required]
    });
  }

  items: Item[] = [
    { id: 1, name: 'Item 1' },
    { id: 2, name: 'Item 2' },
    { id: 3, name: 'Item 3' },
    { id: 4, name: 'Item 4' },
    { id: 5, name: 'Item 5' },
    { id: 6, name: 'Item 2' },
    { id: 7, name: 'Item 3' },
    { id: 8, name: 'Item 4' },
    { id: 9, name: 'Item 5' }
  ];

  selectedItems: Item[] = [];

  onSelectionChange(selected: Item[]): void {
    this.selectedItems = selected;
    console.log('Selected Items:', this.selectedItems);
  }

  ngOnInit(): void {
    this.captureRoutes();
  }

  private captureRoutes() {
    this.route.url.subscribe(url => {
      if (url.length > 0) {
        this.mode = url[0].path as FormMode;;
        console.log('First URL segment:', this.mode);
      }
    });
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
      if (this.id) {
        this.fetchRoleData(this.id);
      }
    });
  }

  fetchRoleData(id: string): void {
    // Fetch the role data based on the roleId
    this.dataService.getRoleById(id).subscribe(role => {
      this.role = role;
    });
  }
  onSave(): void {
    if (this.roleForm.valid) {
      if (this.mode === FormMode.CREATE) {
        // Handle the save action
        this.saveNew();
      } else {
        this.updateExisting();
      }
      console.log('Role details saved:', this.role);
    }
  }

  onCancel() {
    this.router.navigate(['role']);
  }

  private saveNew() {
    this.dataService.createRole(this.roleForm.value as RoleData).subscribe({
      next: (value) => {
        this.errorMessage = null;
        this.router.navigate(['role']);
      },
      error: (e) => {
        this.errorMessage = 'Error saving new role details';
        console.error(e);
      },
      complete: () => console.info('Create role sucessful!'),
    });
  }
  private updateExisting() {
    this.role.name = this.roleForm.value.name;
    this.dataService.updateRole(this.role).subscribe({
      next: (value) => {
        this.errorMessage = null;
        this.router.navigate(['role']);
      },
      error: (e) => {
        this.errorMessage = 'Error saving new role details';
        console.error(e);
      },
      complete: () => console.info('Create role sucessful!'),
    });
  }
}
