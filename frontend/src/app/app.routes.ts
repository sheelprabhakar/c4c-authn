import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './core/auth/auth.guard';
import { TenantComponent } from './pages/tenant/tenant.component';
import { PolicyGuard } from './core/policy/policy.gaurd';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent, canActivate: [PolicyGuard], data: { policies: ['dashboard'], breadcrumb: 'Dashboard' } },
      { path: 'tenant', component: TenantComponent, canActivate: [PolicyGuard], data: { policies: ['tenant management'], breadcrumb: 'Tenant Management' } }, // Add your routes here
    ],
    canActivate: [AuthGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
  }
];
