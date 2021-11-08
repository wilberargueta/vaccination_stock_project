import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { User } from '../../domain/user';
import { UserService } from '../../service/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
  providers: [MessageService]
})
export class ChangePasswordComponent implements OnInit {

  spinner = true;

  @Input()
  user: User = null;

  @Output()
  passwordChanged: EventEmitter<boolean> = new EventEmitter();
  passwordForm: FormGroup = null;

  constructor(
    private userService: UserService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.passwordForm = new FormGroup({
      password: new FormControl(null, [
        Validators.required,
        Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,}$'),
        Validators.minLength(5)])
    }, { updateOn: 'change' });
    this.spinner=false;
  }

  change() {
    if (this.passwordForm.valid) {
      this.user.password = this.passwordForm.get('password').value;
      this.userService.changePassword(this.user).subscribe({
        next: (response) => {
          if (response.ok) {
            this.passwordChanged.emit(true);
            this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Contraseña actualizada.', life: 3000 });
          } else
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible actualizar la contraseña', life: 3000 });
        },
        error: (error) => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible actualizar la contraseña', life: 3000 }); }
      });
    }

  }

}
