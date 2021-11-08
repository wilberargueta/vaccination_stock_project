import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VaccinationRoutingModule } from './vaccination-routing.module';
import { EdithVaccinationComponent } from './components/edith-vaccination/edith-vaccination.component';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageModule } from 'primeng/message';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ToolbarModule } from 'primeng/toolbar';
import { UtilModule } from '../util/util.module';
import { InputNumberModule } from 'primeng/inputnumber';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from '../access/auth/auth.interceptor';

@NgModule({
  declarations: [
    EdithVaccinationComponent
  ],
  imports: [
    CommonModule,
    VaccinationRoutingModule,
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
    InputNumberModule
  ],
  exports: [EdithVaccinationComponent],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }
  ]
})
export class VaccinationModule { }
