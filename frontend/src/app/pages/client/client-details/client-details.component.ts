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
    MatGridListModule,ReactiveFormsModule,
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
    this.dataService.getClientById(clientId).subscribe(client => this.client = client);
  }
  onSave(): void {
    if (this.clientForm.valid) {
      // Handle the save action
      this.dataService.createClient(this.clientForm.value as ClientData).subscribe({
        next: (value) => {
          this.errorMessage = null;
          this.router.navigate(['client']);
        },
        error: (e) => {
          this.errorMessage = 'Error saving client details';
          console.error(e) },
          complete: () => console.info('Create client sucessful!'),
      });
      alert('Client details saved');
    }
    console.log('Client details saved:', this.client);
  }
}
