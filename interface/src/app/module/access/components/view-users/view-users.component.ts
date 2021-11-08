import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { User } from '../../domain/user';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-view-users',
  templateUrl: './view-users.component.html',
  styleUrls: ['./view-users.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class ViewUsersComponent implements OnInit {

  users: User[] = [];
  readonly columns = [
    { field: 'id', header: 'ID' },
    { field: 'username', header: 'Usuario' },
    { field: 'rol', header: 'Rol' }
  ];

  userDialog: boolean;

  user: User;

  selectedUser: User[];

  submitted: boolean;

  cols: any[];
  spinner = true;

  constructor(private userService: UserService, private messageService: MessageService,
    private confirmationService: ConfirmationService) {
  }

  async ngOnInit() {
    await this.userService.getAll().subscribe({
      next: response => {
        if (response.ok)
          this.users = response.content;
        else
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No se pudo consultar los usuarios', life: 3000 });
        this.spinner = false;
      },
      error: error => { }
    });
  }


  async deleteSelectedUser() {

    this.confirmationService.confirm({
      message: 'Esta seguro que quiere eliminar los usuarios seleccionados?',
      header: 'Confirmacion',
      icon: 'pi pi-exclamation-triangle',
      accept: async () => {
        this.spinner = true;
        const userToDelete: User[] = await Promise.all(this.users.filter(val => this.selectedUser.includes(val)));
        await this.userService.deleteList(userToDelete).subscribe(response => {
          if (response.ok) {
            this.users = this.users.filter(val => !this.selectedUser.includes(val));
            this.selectedUser = null;
            this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Usuarios eliminados', life: 3000 });
          } else
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar los usuarios', life: 3000 });
          this.spinner = false;
        },
          error => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar los usuarios', life: 3000 });
            this.spinner = false;
          });

      }
    });
  }

  editUser(user: User) {
    this.user = { ...user };
    this.userDialog = true;
  }

  deleteUser(user: User) {
    this.confirmationService.confirm({
      message: `Esta seguro de eliminar el usuario ${user.username} ?`,
      header: 'Confirmacion',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.spinner = true;
        this.userService.delete(user.id).subscribe({
          next: (response) => {
            if (response.ok) {
              this.users = this.users.filter(val => val.id !== user.id);
              this.user = {};
              this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Usuario eliminado correctamente', life: 3000 });
            } else
              this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar el usuario', life: 3000 });
            this.spinner = false;
          }, error: (error) => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible eliminar el usuario', life: 3000 });
            this.spinner = false;
          }
        })

      }
    });
  }

  hideDialog() {
    this.userDialog = false;
    this.submitted = false;
  }


  findIndexById(id: number): number {

    return this.users.findIndex((user) => { user.id === id });
  }
}