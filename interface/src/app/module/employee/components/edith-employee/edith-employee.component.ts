import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Employee } from '../../domain/employee';
import { EmployeeService } from '../../services/employee.service';
import * as moment from 'moment';
import { UserService } from 'src/app/module/access/service/user.service';
import { User } from 'src/app/module/access/domain/user';
import { PerviousRouterService } from 'src/app/module/util/service/previous-router.service';
import { Observer } from 'rxjs';
import { Response } from 'src/app/module/util/response';

@Component({
  selector: 'app-edith-employee',
  templateUrl: './edith-employee.component.html',
  styleUrls: ['./edith-employee.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class EdithEmployeeComponent implements OnInit {
  employeeForm: FormGroup = Object.create(null);
  formUserValid: Boolean = false;
  isValidDocumentNumber = false;
  update = true;
  documentNumberTemporal: string = null;
  spinner = true;
  user: User = null;
  employee: Employee = null;
  constructor(
    private employeeService: EmployeeService,
    private messageService: MessageService,
    private userService: UserService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private activeRouter: ActivatedRoute,
    public prepUrl: PerviousRouterService
  ) { }
  async ngOnInit() {
    this.employeeForm = new FormGroup({
      documentNumber: new FormControl(null, [Validators.required, Validators.pattern(/^\d{10,10}$/)]),
      firstName: new FormControl(null, [Validators.required, Validators.minLength(3)]),
      lastName: new FormControl(null, [Validators.required, Validators.minLength(3)]),
      email: new FormControl(null, [Validators.required, Validators.pattern('^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$')]),
      birthday: new FormControl(null, [Validators.required]),
      address: new FormControl(null, [Validators.required, Validators.minLength(4)]),
      phone: new FormControl(null, [Validators.required, Validators.pattern(/^\+\(\d{1,3}\)\s{1,1}\d{3,4}\-\d{4,4}$/)])
    }, { updateOn: 'change' });
    const id = this.activeRouter.snapshot.paramMap.get('id');
    if (id !== null) {
      const employee: Employee = await this.getEmployeeById(Number(id));
      this.isValidDocumentNumber = true;
      this.update = true;
      this.employee = employee;
      this.documentNumberTemporal = employee.documentNumber;
      this.employeeForm.get('documentNumber').setValue(employee.documentNumber);
      this.employeeForm.get('firstName').setValue(employee.firstName);
      this.employeeForm.get('lastName').setValue(employee.lastName);
      this.employeeForm.get('email').setValue(employee.email);
      this.employeeForm.get('birthday').setValue(employee.birthday);
      this.employeeForm.get('address').setValue(employee.address);
      this.employeeForm.get('phone').setValue(employee.phone);
      this.user = employee.userAccess;
    } else
      this.update = false;

    this.spinner = false;
  }
  saveEmployee(user: User) {
    if (user === null || user === undefined) {
      this.spinner = false;
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible guardar el empleado', life: 3000 });
      return;
    }

    const employee: Employee = { ...this.employeeForm.getRawValue() };
    employee.birthday = moment(employee.birthday).format('YYYY-MM-DD');
    employee.userAccess = user;
    employee.vaccinated = false;
    if (this.update) {
      employee.id = this.employee.id
      employee.vaccinated = this.employee.vaccinated;
      this.employeeService.update(employee).subscribe(this.subscribeOnSaveUser(true));
    }
    else
      this.employeeService.save(employee).subscribe(this.subscribeOnSaveUser(true));
    this.router.navigate([this.prepUrl.getPreviousUrl()]);
  }
  getEmployeeById(id: number): Promise<Employee> {
    return new Promise((resolve, reject) => {
      this.employeeService.getById(id).subscribe({
        next: (response) => {
          resolve(response.content);
        }, error: (error) => {
          resolve(null);
        }
      });
    });
  }
  saveUser() {

    if (this.employeeForm.invalid && !this.formUserValid) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Los datos ingresados en el formulario estan incorrectos', life: 3000 });
      return;
    }
    this.spinner = true;
    this.userService.userEventSuscribe.next(null);

  }
  updateFormUserValid(value: Boolean) {
    this.formUserValid = value;
  }
  validateDocumentNumber(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.employeeService.validateDocumentNumber(this.employeeForm.get('documentNumber').value).subscribe({
        next: (response) => {
          if (response.ok && response.content)
            this.isValidDocumentNumber = false
          else
            this.isValidDocumentNumber = true;
        }, error: (error) => this.isValidDocumentNumber = false
      })
    });
  }
  subscribeOnSaveUser(notify: boolean = true): Observer<Response<Employee>> {
    return {
      next: (response) => {
        if (response.ok) {
          if (notify)
            this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Empleado guardado correctamente', life: 3000 });
          this.router.navigate([this.prepUrl.getPreviousUrl()]);
        }
        else {
          if (notify)
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible guardar el empleado', life: 3000 });

        }
      }, error: (error) => {

        if (notify)
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible guardar el empleado', life: 3000 });
      },
      complete: () => { }
    };
  }
}
