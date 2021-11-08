import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthAdminEmployeeGuard } from '../access/auth/auth-admin-employee.guard';
import { AuthAdminGuard } from '../access/auth/auth-admin.guard';
import { EdithEmployeeComponent } from './components/edith-employee/edith-employee.component';
import { MainEmployeeComponent } from './components/main-employee/main-employee.component';
import { ViewEmployeeComponent } from './components/view-employee/view-employee.component';

const routes: Routes = [
  {
    path: '',
    component: MainEmployeeComponent,
    canActivate: [AuthAdminGuard]
  },
  {
    path: 'edith',
    component: EdithEmployeeComponent,
    canActivate: [AuthAdminGuard]
  },
  {
    path: 'edith/:id',
    component: EdithEmployeeComponent,
    canActivate: [AuthAdminEmployeeGuard]
  },
  {
    path: 'view/:id',
    component: ViewEmployeeComponent,
    canActivate: [AuthAdminEmployeeGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmployeeRoutingModule { }
