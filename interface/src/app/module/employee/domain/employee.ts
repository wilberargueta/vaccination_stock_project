import { User } from "../../access/domain/user";
import { VaccinationStock } from "../../vaccination/domain/vaccination-stock";

export interface Employee {
    id?: number;
    documentNumber?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    birthday?: string;
    address?: string;
    phone?: string;
    vaccinated?: boolean;
    delete?: boolean;
    userAccess?: User;
    vaccinationStock?: VaccinationStock[];
}
