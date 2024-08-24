import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule } from '@angular/forms';
import { AuthService } from 'src/app/core/auth/auth.service';

import { Router, RouterModule } from '@angular/router';
import { UserDataService } from 'src/app/core/user/user.data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  standalone: true,
  imports: [
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCheckboxModule,
    RouterModule,
  ],
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(
    private authService: AuthService,
    private userDataService: UserDataService,
    private router: Router
  ) { }

  onLogin() {
    this.authService.login(this.username, this.password).subscribe({
      next: (v) => {
        this.userDataService.getDetail().subscribe({
          next: (v) => {
            this.router.navigate(['dashboard']); // Navigate to the home
          },
          error: (e) => console.error(e),
          complete: () => console.info('Fetch User details complete'),
        });
        //console.log(v);
      },
      error: (e) => console.error(e),
      complete: () => console.info('Login complete'),
    });
  }
}
