import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, ResolveStart, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { User } from 'src/app/module/access/domain/user';
import { UserService } from 'src/app/module/access/service/user.service';
import { PerviousRouterService } from 'src/app/module/util/service/previous-router.service';
import { VaccinationStock } from 'src/app/module/vaccination/domain/vaccination-stock';
import { Employee } from '../../domain/employee';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-view-employee',
  templateUrl: './view-employee.component.html',
  styleUrls: ['./view-employee.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class ViewEmployeeComponent implements OnInit {

  employee: Employee = null;
  spinner = true;
  showModalAddVaccination = false;
  constructor(
    private employeeService: EmployeeService,
    private messageService: MessageService,
    private userService: UserService,
    private confirmationService: ConfirmationService,
    private activeRouter: ActivatedRoute,
    private route: Router,
    public previousRouterService: PerviousRouterService
  ) {
  }

  ngOnInit(): void {
    const id = Number(this.activeRouter.snapshot.paramMap.get('id'));
    if (id !== null && !Number.isNaN(id)) {
      this.employeeService.getById(id).subscribe({
        next: async (response) => {

          if (response.ok) {
            this.employee = response.content;
            const user: User = await this.getUserAndRole(this.employee.userAccess.id);
            this.employee.userAccess = user;
            this.spinner = false;
          }
          else {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se encontro el empleado', life: 3000 });
            setTimeout(() => this.route.navigate([this.previousRouterService.getPreviousUrl()]), 3000);
          }

        }, error: error => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible encontrar el empleado', life: 3000 });
          setTimeout(() => this.route.navigate([this.previousRouterService.getPreviousUrl()]), 3000);
        }
      });
    }
  }

  getUserAndRole(id: number): Promise<User> {
    return new Promise((resolve, reject) => {
      this.userService.getById(id).subscribe({ next: (response) => resolve(response.content), error: (error => resolve(null)) });
    });
  }
  async vaccinationSaved(vaccination: VaccinationStock) {
    if (vaccination !== null) {
      this.employee.vaccinationStock.push(vaccination);
      this.employee.vaccinated = true;
      await this.employeeService.updateVaccinate(this.employee.id, true).subscribe();
    }
    this.showModalAddVaccination = false;
  }



}
