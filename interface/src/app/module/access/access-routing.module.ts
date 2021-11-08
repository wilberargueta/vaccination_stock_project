import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthAdminGuard } from './auth/auth-admin.guard';
import { EdithUserComponent } from './components/edith-user/edith-user.component';
import { LoginComponent } from './components/login/login.component';
import { ViewUsersComponent } from './components/view-users/view-users.component';

const routes: Routes = [
  { path: 'edith', component: EdithUserComponent, canActivate: [AuthAdminGuard] },
  { path: 'edith/:id', component: EdithUserComponent, canActivate: [AuthAdminGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'view', component: ViewUsersComponent, canActivate: [AuthAdminGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccessRoutingModule { }
