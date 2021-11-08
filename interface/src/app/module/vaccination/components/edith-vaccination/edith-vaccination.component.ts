import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Observer } from 'rxjs';
import { Employee } from 'src/app/module/employee/domain/employee';
import { Response } from 'src/app/module/util/response';
import { VaccinationStock } from '../../domain/vaccination-stock';
import { VaccinationService } from '../../service/vaccination.service';
import * as moment from 'moment';

@Component({
  selector: 'app-edith-vaccination',
  templateUrl: './edith-vaccination.component.html',
  styleUrls: ['./edith-vaccination.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class EdithVaccinationComponent implements OnInit {

  @Input()
  disableSaveButton: boolean = false;

  @Input()
  employee: Employee;

  @Output()
  vaccinationSaved: EventEmitter<VaccinationStock> = new EventEmitter();

  @Output()
  formVaccinationValid: EventEmitter<Boolean> = new EventEmitter();
  spinner = true;
  updateVaccination: boolean = false;

  buttonSaveText = 'Guardar';
  vaccinationForm: FormGroup = Object.create(null);
  vaccinationOptions: { name: string, value: string, id: number }[] = [];
  constructor(
    private vaccinationService: VaccinationService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private activeRouter: ActivatedRoute
  ) { }

  async ngOnInit() {
    this.vaccinationOptions = this.vaccinationService.getVaccinateOptions();
    this.vaccinationService.vaccinationEventSuscribe.subscribe({ next: (e) => this.save(false) })
    this.vaccinationForm = new FormGroup({
      type: new FormControl(null, [Validators.required]),
      date: new FormControl(null, [Validators.required]),
      dose: new FormControl('0', [Validators.required, Validators.pattern(/^[1-9]+\d*$/)])
    }, { updateOn: 'change' });
    this.vaccinationForm.valueChanges.subscribe({ next: (c) => this.formVaccinationValid.emit(this.vaccinationForm.valid) });

    const id = Number(this.activeRouter.snapshot.paramMap.get('id'));
    if (id !== null && Number.isNaN(id)) {
      this.updateVaccination = true;
      this.buttonSaveText = 'Actualizar';
      const vaccination: VaccinationStock = await this.getVaccinationById(id);
      this.vaccinationForm.get('type').setValue(vaccination.type);
      this.vaccinationForm.get('date').setValue(vaccination.date);
      this.vaccinationForm.get('dose').setValue(vaccination.dose);
      this.employee = vaccination.employee;
    } else {
      this.updateVaccination = false;
      this.buttonSaveText = 'Guardar';
    }
    this.spinner = false;
  }

  save(notify: boolean = true) {

    if (this.vaccinationForm.invalid) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Los datos ingresados en el formulario estan incorrectos', life: 3000 });
      return;
    }
    this.spinner = true;
    const vaccination: VaccinationStock = { ...this.vaccinationForm.getRawValue() };
    vaccination.date = moment(vaccination.date).format('YYYY-MM-DD');
    vaccination.employee = this.employee;
    vaccination.type = vaccination.type['value'];
    if (this.updateVaccination)
      this.vaccinationService.update(vaccination).subscribe(this.subscribeOnSaveVaccination(notify));
    else
      this.vaccinationService.save(vaccination).subscribe(this.subscribeOnSaveVaccination(notify));
  }


  getVaccinationById(id: number): Promise<VaccinationStock> {
    return new Promise((resolve, reject) => {
      this.vaccinationService.getById(id).subscribe({
        next: (response) => {
          resolve(response.content);
        }, error: (error) => {
          resolve(null);
        }
      });
    });
  }


  subscribeOnSaveVaccination(notify: boolean = true): Observer<Response<VaccinationStock>> {
    return {
      next: (response) => {
        if (response.ok) {
          this.vaccinationSaved.emit(response.content);
          if (notify)
            this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Vacuna agregada correctamente', life: 3000 });
        }
        else {
          this.vaccinationSaved.emit(null);
          if (notify)
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible agregar la vacuna', life: 3000 });
        }
        this.spinner = false;
      }, error: (error) => {
        this.spinner = false;
        this.vaccinationSaved.emit(null);
        if (notify)
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible agregar la vacuna', life: 3000 });
      },
      complete: () => { }
    };
  }

  cancelSave() {
    this.vaccinationSaved.emit(null);
  }

}
