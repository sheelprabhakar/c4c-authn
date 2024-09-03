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
import { ClientData } from 'src/app/core/client/client.data.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ClientDataService } from 'src/app/core/client/client.data.service';
import { C4cListComponent } from '../../../shared/c4c-list/c4c-list.component';
export interface Item {
  id: number;
  name: string;
}
@Component({
  selector: 'app-client-details',
  templateUrl: './client-details.component.html',
  styleUrl: './client-details.component.scss',
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
export class ClientDetailsComponent {

  client: ClientData = {
    name: '',
    clientId: '',
    clientSecret: '',
    tenantId: '',
    // Initialize other properties as needed
  };

  mode: FormMode;
  clientId: string;
  clientForm: FormGroup;
  errorMessage: string

  constructor(private route: ActivatedRoute, private router: Router,
    private dataService: ClientDataService,
    private fb: FormBuilder,
  ) {
    this.clientForm = this.fb.group({
      name: ['', Validators.required],
      clientId: [{ value: '', disabled: true }],
      clientSecret: [{ value: '', disabled: true }]
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
      this.clientId = params.get('id');
      if (this.clientId) {
        this.fetchClientData(this.clientId);
      }
    });
  }

  fetchClientData(clientId: string): void {
    // Fetch the client data based on the clientId
    this.dataService.getClientById(clientId).subscribe(client => {
      this.client = client;
    });
  }
  onSave(): void {
    if (this.clientForm.valid) {
      if (this.mode === FormMode.CREATE) {
        // Handle the save action
        this.saveNew();
      } else {
        this.updateExisting();
      }
      console.log('Client details saved:', this.client);
    }
  }

  onCancel() {
    this.router.navigate(['client']);
  }
  private saveNew() {
    this.dataService.createClient(this.clientForm.value as ClientData).subscribe({
      next: (value) => {
        this.errorMessage = null;
        this.router.navigate(['client']);
      },
      error: (e) => {
        this.errorMessage = 'Error saving new client details';
        console.error(e);
      },
      complete: () => console.info('Create client sucessful!'),
    });
  }
  private updateExisting() {
    this.client.name = this.clientForm.value.name;
    this.dataService.updateClient(this.client).subscribe({
      next: (value) => {
        this.errorMessage = null;
        this.router.navigate(['client']);
      },
      error: (e) => {
        this.errorMessage = 'Error saving new client details';
        console.error(e);
      },
      complete: () => console.info('Create client sucessful!'),
    });
  }
}
