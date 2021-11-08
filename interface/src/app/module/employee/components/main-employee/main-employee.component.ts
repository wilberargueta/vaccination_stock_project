import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Employee } from '../../domain/employee';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-main-employee',
  templateUrl: './main-employee.component.html',
  styleUrls: ['./main-employee.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class MainEmployeeComponent implements OnInit {

  employees: Employee[] = [];
  readonly columns = [
    { field: 'id', header: 'ID' },
    { field: 'documentNumber', header: 'Cedula' },
    { field: 'firstName', header: 'Nombres' },
    { field: 'lastName', header: 'Apellidos' },
    { field: 'email', header: 'Correo' },
    { field: 'birthday', header: 'Fecha de nacimiento' },
    { field: 'address', header: 'Direccion' },
    { field: 'phone', header: 'Telefono' },
    { field: 'vaccinated', header: 'Vacunado' },
  ];

  employeeDialog: boolean;

  employee: Employee;

  selectedEmployees: Employee[];

  submitted: boolean;

  cols: any[];
  spinner = true;

  constructor(private employeeService: EmployeeService, private messageService: MessageService,
    private confirmationService: ConfirmationService) {
  }

  async ngOnInit() {
    await this.employeeService.getAll().subscribe({
      next: response => {
        if (response.ok)
          this.employees = response.content;
        else
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo consultar los empleados', life: 3000 });
        this.spinner = false;
      },
      error: error => { }
    });
  }

  openNew() {
    this.employee = {};
    this.submitted = false;
    this.employeeDialog = true;
  }

  async deleteSelectedEmployee() {

    this.confirmationService.confirm({
      message: 'Esta seguro que quiere eliminar los empleados seleccionados?',
      header: 'Confirmacion',
      icon: 'pi pi-exclamation-triangle',
      accept: async () => {
        this.spinner = true;
        const employeesToDelete: Employee[] = await Promise.all(this.employees.filter(val => this.selectedEmployees.includes(val)));
        await this.employeeService.deleteList(employeesToDelete).subscribe(response => {
          if (response.ok) {
            this.employees = this.employees.filter(val => !this.selectedEmployees.includes(val));
            this.selectedEmployees = null;
            this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Empleados eliminados', life: 3000 });
          } else
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar los empleados', life: 3000 });
          this.spinner = false;
        },
          error => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar los empleados', life: 3000 });
            this.spinner = false;
          });

      }
    });
  }

  editEmployee(employee: Employee) {
    this.employee = { ...employee };
    this.employeeDialog = true;
  }

  deleteEmployee(employee: Employee) {
    this.confirmationService.confirm({
      message: `Esta seguro de eliminar el empleado ${employee.firstName} ${employee.lastName} ?`,
      header: 'Confirmacion',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner = true;
        this.employeeService.delete(employee.id).subscribe({
          next: (response) => {
            if (response.ok) {
              this.employees = this.employees.filter(val => val.id !== employee.id);
              this.employee = {};
              this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Empleado eliminado correctamente', life: 3000 });
            } else
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar el empleado', life: 3000 });
            this.spinner = false;
          }, error: (error) => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar el empleado', life: 3000 });
            this.spinner = false;
          }
        })

      }
    });
  }

  hideDialog() {
    this.employeeDialog = false;
    this.submitted = false;
  }


  findIndexById(id: number): number {

    return this.employees.findIndex((employee) => { employee.id === id });
  }

  async loadAll(){
    this.spinner =true;
    await this.employeeService.getAll().subscribe({
      next: response => {
        if (response.ok)
          this.employees = response.content;
        else
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo consultar los empleados', life: 3000 });
        this.spinner = false;
      },
      error: error => { }
    });
  }



}
