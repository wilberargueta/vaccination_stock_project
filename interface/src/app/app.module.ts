import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuModule } from 'primeng/menu';
import { MegaMenuModule } from 'primeng/megamenu';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { SidebarModule } from 'primeng/sidebar';
import { ButtonModule } from 'primeng/button';
import { InputSwitchModule } from 'primeng/inputswitch';
import { RadioButtonModule } from 'primeng/radiobutton';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppCodeModule } from './module/root/app-code-module/app.code.component';
import { MainComponent } from './module/root/main/main.component';
import { BreadcrumbComponent } from './module/root/breadcrumb/breadcrumb.component';
import { ConfigComponent } from './module/root/config/config.component';
import { FooterComponent } from './module/root/footer/footer.component';
import { AppRightMenuComponent } from './module/root/header/app.rightmenu.component';
import { AppTopBarComponent } from './module/root/header/app.topbar.component';
import { InlineMenuComponent } from './module/root/inline-menu/inline-menu.component';
import { MenuComponent } from './module/root/menu/menu.component';
import { MenuitemComponent } from './module/root/menuitem/menuitem.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { AccessDeniedComponent } from './module/root/access-denied/access-denied.component';
import { AuthInterceptor } from './module/access/auth/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    BreadcrumbComponent,
    ConfigComponent,
    FooterComponent,
    AppRightMenuComponent,
    AppTopBarComponent,
    InlineMenuComponent,
    MenuComponent,
    MenuitemComponent,
    AccessDeniedComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MegaMenuModule,
    MenuModule,
    BreadcrumbModule,
    SidebarModule,
    ButtonModule,
    InputSwitchModule,
    RadioButtonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppCodeModule,
    AppRoutingModule,
    ConfirmDialogModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
