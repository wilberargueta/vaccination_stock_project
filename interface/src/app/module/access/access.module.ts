import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccessRoutingModule } from './access-routing.module';
import { EdithUserComponent } from './components/edith-user/edith-user.component';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageModule } from 'primeng/message';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ToolbarModule } from 'primeng/toolbar';
import { LoginComponent } from './components/login/login.component';
import { UtilModule } from '../util/util.module';
import { ViewUsersComponent } from './components/view-users/view-users.component';
import { TableModule } from 'primeng/table';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth/auth.interceptor';


@NgModule({
  declarations: [
    EdithUserComponent,
    LoginComponent,
    ViewUsersComponent,
    ChangePasswordComponent
  ],
  imports: [
    CommonModule,
    AccessRoutingModule,
    DropdownModule,
    FormsModule,
    ReactiveFormsModule,
    ToastModule,
    ButtonModule,
    ConfirmDialogModule,
    MessageModule,
    InputTextModule,
    PasswordModule,
    ToolbarModule,
    UtilModule,
    TableModule,
    OverlayPanelModule
  ],
  exports: [EdithUserComponent, ChangePasswordComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }

  ]
})
export class AccessModule { }
