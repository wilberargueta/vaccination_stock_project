import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanelModule } from 'primeng/panel';
import { EmployeeRoutingModule } from './employee-routing.module';
import { MainEmployeeComponent } from './components/main-employee/main-employee.component';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { RatingModule } from 'primeng/rating';
import { UtilModule } from '../util/util.module';
import { BadgeModule } from 'primeng/badge';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { EdithEmployeeComponent } from './components/edith-employee/edith-employee.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputMaskModule } from 'primeng/inputmask';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { AccessModule } from '../access/access.module';
import { ViewEmployeeComponent } from './components/view-employee/view-employee.component';
import { AvatarModule } from 'primeng/avatar';
import { BlockUIModule } from 'primeng/blockui';
import { DialogModule } from 'primeng/dialog';
import { VaccinationModule } from '../vaccination/vaccination.module';
import { BrowserModule } from '@angular/platform-browser';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { FilterEmployeeComponent } from './components/filter-employee/filter-employee.component';
import { DropdownModule } from 'primeng/dropdown';
import { SelectButtonModule } from 'primeng/selectbutton';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from '../access/auth/auth.interceptor';
@NgModule({
  declarations: [
    MainEmployeeComponent,
    EdithEmployeeComponent,
    ViewEmployeeComponent,
    FilterEmployeeComponent
  ],
  imports: [
    CommonModule,
    BadgeModule,
    RatingModule,
    TableModule,
    ToolbarModule,
    PanelModule,
    MessageModule,
    EmployeeRoutingModule,
    UtilModule,
    ToastModule,
    ButtonModule,
    ConfirmDialogModule,
    FormsModule,
    ReactiveFormsModule,
    InputMaskModule,
    InputTextModule,
    AccessModule,
    CardModule,
    AvatarModule,
    BlockUIModule,
    DialogModule,
    VaccinationModule,
    OverlayPanelModule,
    DropdownModule,
    SelectButtonModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }

  ]
})
export class EmployeeModule { }
