import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MomentFormatPipe } from './moment-format.pipe';
import { SpinnerLoadComponent } from './spinner-load/spinner-load.component';
import {ProgressSpinnerModule} from 'primeng/progressspinner';



@NgModule({
  declarations: [
    MomentFormatPipe,
    SpinnerLoadComponent
  ],
  imports: [
    CommonModule,
    ProgressSpinnerModule
  ],
  exports: [
    SpinnerLoadComponent,
    MomentFormatPipe
  ]
})
export class UtilModule { }
