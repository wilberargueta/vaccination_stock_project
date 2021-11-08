import { Employee } from "../../employee/domain/employee";

export interface VaccinationStock {
    id?: number;
    type?: string;
    date?: string;
    dose?: string;
    employee?: Employee;
}
