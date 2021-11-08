import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthAdminEmployeeGuard } from './module/access/auth/auth-admin-employee.guard';
import { AuthAdminGuard } from './module/access/auth/auth-admin.guard';
import { LoginComponent } from './module/access/components/login/login.component';
import { AccessDeniedComponent } from './module/root/access-denied/access-denied.component';
import { MainComponent } from './module/root/main/main.component';

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'admin',
        children: [
          {
            path: 'employee',
            loadChildren: () => import('./module/employee/employee.module').then(m => m.EmployeeModule),
            canActivate: [AuthAdminEmployeeGuard]
          },
          {
            path: 'vaccination',
            loadChildren: () => import('./module/vaccination/vaccination.module').then(m => m.VaccinationModule),
            canActivate: [AuthAdminEmployeeGuard]
          },
          {
            path: 'access',
            loadChildren: () => import('./module/access/access.module').then(m => m.AccessModule),
            canActivate: [AuthAdminEmployeeGuard]
          }

        ]
      }

    ],
    canActivate: [AuthAdminEmployeeGuard]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'access-denied',
    component: AccessDeniedComponent
  },
  {
    path: '**',
    redirectTo: '/'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
