import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LayoutComponent } from './shared/layout/layout.component';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './core/auth/auth.guard';
import { TenantComponent } from './pages/tenant/tenant.component';
import { PolicyGuard } from './core/policy/policy.gaurd';
import { TenantDetailsComponent } from './pages/tenant/tenant-details/tenant-details.component';
import { ClientComponent } from './pages/client/client.component';
import { ClientDetailsComponent } from './pages/client/client-details/client-details.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent, canActivate: [PolicyGuard], data: { policies: ['dashboard'], breadcrumb: 'Dashboard' } },
      {
        path: 'tenant', component: TenantComponent, canActivate: [PolicyGuard], data: { policies: ['tenant management'], breadcrumb: 'Tenant Management' },
        children:[
          {path: 'create', component: TenantDetailsComponent, canActivate: [PolicyGuard],
            data: { policies: ['post:tenant management'], breadcrumb: 'Create'}},
          {path: 'edit/:id', component: TenantDetailsComponent, canActivate: [PolicyGuard],
            data: { policies: ['put:tenant management', 'patch:tenant management'], breadcrumb: 'Edit'}}
        ]
      },
      {
        path: 'client', component: ClientComponent, canActivate: [PolicyGuard], data: { policies: ['client management'], breadcrumb: 'Client Management' },
        children:[
          {path: 'create', component: ClientDetailsComponent, canActivate: [PolicyGuard],
            data: { policies: ['post:client management'], breadcrumb: 'Create'}},
          {path: 'edit/:id', component: ClientDetailsComponent, canActivate: [PolicyGuard],
            data: { policies: ['put:client management', 'patch:client management'], breadcrumb: 'Edit'}}
        ]
      }, // Add your routes here
    ],
    canActivate: [AuthGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
  }
];
