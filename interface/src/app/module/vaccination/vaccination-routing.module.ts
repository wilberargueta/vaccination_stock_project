import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EdithVaccinationComponent } from './components/edith-vaccination/edith-vaccination.component';

const routes: Routes = [
  { path: 'edith', component: EdithVaccinationComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VaccinationRoutingModule { }
