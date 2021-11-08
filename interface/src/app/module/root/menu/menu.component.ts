import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { JwtToken } from '../../access/domain/jwt-token';
import { User } from '../../access/domain/user';
import { Employee } from '../../employee/domain/employee';
import { EmployeeService } from '../../employee/services/employee.service';
import { SessionStorageService } from '../../util/service/session-storage.service';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {


    model: any[];

    constructor(
        public app: AppComponent,
        private employeeService: EmployeeService,
        private sessionService: SessionStorageService,
        private router: Router
    ) { }

    async ngOnInit() {
        const jwt: JwtToken = this.sessionService.getValue<JwtToken>('session_token');
        const employee: Employee = await this.getEmployee(jwt.id);
        this.sessionService.setEmployee(employee);
        switch (jwt.role.rol) {
            case 'ADMIN':
                this.model = [{
                    label: 'Administracion', icon: 'pi pi-fw pi-th-large',
                    items: [
                        { label: 'Empleados', icon: 'pi pi-fw pi-users', routerLink: ['/admin/employee'], },
                        { label: 'Usuarios', icon: 'pi pi-fw pi-users', routerLink: ['/admin/access/view'], },
                        (employee !== null) ? { label: 'Empleado', icon: 'pi pi-fw pi-users', routerLink: ['/admin/employee/view/' + employee.id], } : {}
                    ]
                }];
                // if (employee !== null)
                //     this.model.push({ label: 'Empleado', icon: 'pi pi-fw pi-users', routerLink: ['/admin/employee/view/' + employee.id], });
                break;

            case 'EMPLOYEE':
                if (employee !== null)
                    this.model = [{
                        label: 'Perfiles', icon: 'pi pi-fw pi-th-large',
                        items: [
                            { label: 'Empleado', icon: 'pi pi-fw pi-users', routerLink: ['/admin/employee/view/' + employee.id], },
                            // { label: 'Usuario', icon: 'pi pi-fw pi-users', routerLink: ['/admin/access/edith/' + jwt.id], }
                        ]
                    }];
                break;
            default:
                this.model = [

                ]
                break;
        }

    }
    getEmployee(id: number): Promise<Employee> {
        return new Promise((resolve, reject) => {
            const user: User = { id };
            this.employeeService.getByUserAccess(user).subscribe({
                next: (response) => {
                    resolve(response.content);
                }, error: (error) => {
                    resolve(null);
                }
            });
        });
    }
}
