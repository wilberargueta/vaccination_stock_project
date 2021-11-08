import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { VaccinationService } from 'src/app/module/vaccination/service/vaccination.service';
import { EmployeeService } from '../../services/employee.service';
import * as moment from 'moment';
import { Employee } from '../../domain/employee';
import { Observer } from 'rxjs';
import { Response } from 'src/app/module/util/response';

@Component({
  selector: 'app-filter-employee',
  templateUrl: './filter-employee.component.html',
  styleUrls: ['./filter-employee.component.css']
})
export class FilterEmployeeComponent implements OnInit {

  @Output()
  employees: EventEmitter<Employee[]> = new EventEmitter();

  @Output()
  spinner: EventEmitter<boolean> = new EventEmitter();

  @Output()
  loadAll: EventEmitter<boolean> = new EventEmitter();
  
  readonly dropdownOptions = [
    { id: 1, name: 'Estado de vacunación' },
    { id: 2, name: 'Tipo de vacuna' },
    { id: 3, name: 'Rango de fecha de vacunación' }
  ];
  readonly stateOptions = [
    { id: 1, name: 'Vacunado', value: true },
    { id: 2, name: 'No Vacunado', value: false }
  ]
  selectedOptions: { id: number, name: string, value: boolean } = null;
  selectedOptionsState = true;
  typeVaccination: { name: string, value: string, id: number } = null;
  optionsTypeVaccinate: { name: string, value: string, id: number }[] = [];
  start: string = '';
  end: string = '';

  constructor(
    private employeeService: EmployeeService,
    public vaccinationService: VaccinationService
  ) { }

  ngOnInit(): void {
    this.start = moment().add(2, 'days').format('YYYY-MM-DD');
    this.end = moment().format('YYYY-MM-DD');
    this.optionsTypeVaccinate = this.vaccinationService.getVaccinateOptions();
  }

  filter() {
    switch (this.selectedOptions.id) {
      case 1:
        this.filterByState();
        break;
      case 2:
        this.filterByTypeVaccinate();
        break;
      case 3:
        this.filterByDate();
        break;
      default:
        break;
    }
  }
  filterByState() {
    this.employeeService.getByState(this.selectedOptionsState).subscribe(this.subscribeOnFilter());
  }

  filterByTypeVaccinate() {
    this.employeeService.getByType(this.typeVaccination.value).subscribe(this.subscribeOnFilter());
  }
  filterByDate() {
    this.employeeService.getByDate(moment(this.start).format('YYYY-MM-DD'), moment(this.end).format('YYYY-MM-DD')).subscribe(this.subscribeOnFilter());
  }

  subscribeOnFilter(): Observer<Response<Employee[]>> {
    return {
      next: (response) => {
        this.spinner.emit(false);
        this.employees.emit(response.content);
      }, error: (error) => {
        this.spinner.emit(false);
        this.employees.emit(null);
      },
      complete: () => { }
    };
  }

}
