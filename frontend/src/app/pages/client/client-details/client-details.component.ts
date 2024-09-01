import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

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
    MatGridListModule,
  ]
})
export class ClientDetailsComponent {
  client = {
    name: '',
    clientid: '',
    clientsecret: ''
  };

  constructor() {}

  ngOnInit(): void {
    // Initialize the client model with data
    this.client = {
      name: 'Example Client',
      clientid: '123456',
      clientsecret: 'abcdef'
    };
  }

  onSave(): void {
    // Handle save logic here
    console.log('Client details saved:', this.client);
  }
}
