import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TranslocoRootModule } from './transloco-root.module';
import { PolicyService } from './core/policy/policy.service';
import { AuthService } from './core/auth/auth.service';
import { LoaderComponent } from './shared/loader/loader.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, TranslocoRootModule, LoaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent  implements OnInit {
  title = 'Angular Starter!';

    constructor(private policyService: PolicyService, private authService: AuthService) {}

    ngOnInit(): void {
      if (this.authService.loggedIn) {
        this.policyService.init();
      }
    }
  }
