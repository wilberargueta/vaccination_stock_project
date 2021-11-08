import { AfterContentInit, AfterViewInit, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Rol } from '../../domain/rol';
import { RolService } from '../../service/rol.service';
import { UserService } from '../../service/user.service';
import { User } from '../../domain/user';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Observer } from 'rxjs';
import { Response } from 'src/app/module/util/response';
import { PerviousRouterService } from 'src/app/module/util/service/previous-router.service';
@Component({
  selector: 'app-edith-user',
  templateUrl: './edith-user.component.html',
  styleUrls: ['./edith-user.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class EdithUserComponent implements OnInit {

  @Input()
  disableSaveButton: boolean = false;

  @Input()
  user: User = null;


  @Output()
  userSaved: EventEmitter<User> = new EventEmitter();

  @Output()
  formUserValid: EventEmitter<Boolean> = new EventEmitter();

  updateUser: boolean = false;


  userValid: boolean = false;

  usernameTemporal: string = null;

  roles: Rol[] = [];
  rolSelected: Rol = null;
  buttonSaveText = 'Guardar';
  userForm: FormGroup = Object.create(null);

  constructor(
    private rolService: RolService,
    private userService: UserService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private activeRouter: ActivatedRoute,
    public preUrl: PerviousRouterService,
    private router: Router
  ) { }

  async ngOnInit() {
    this.userService.userEventSuscribe.subscribe({ next: (e) => this.save(false) })
    this.rolService.getAll().subscribe({
      next: (response) => {
        if (response.ok)
          this.roles = response.content;
      }, error: (error) => { }
    });

    this.userForm = new FormGroup({
      username: new FormControl(null, [Validators.required, Validators.minLength(4)]),
      password: new FormControl(null, [Validators.required, Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,}$'), Validators.minLength(5)]),
      rol: new FormControl('0', [Validators.required])
    }, { updateOn: 'change' });
    this.userForm.valueChanges.subscribe({ next: (c) => this.formUserValid.emit((this.userValid) ? this.userForm.valid : false) });

    let id = this.activeRouter.snapshot.paramMap.get('id');

    if (id === null && this.user !== null)
      id = this.user.id.toString();
    if (id !== null) {
      this.updateUser = true;
      this.buttonSaveText = 'Actualizar';
      const user: User = await this.getUserById(Number(id));
      this.userForm.get('username').setValue(user.username);
      this.userForm.get('password').setValue(user.password);
      this.userForm.get('rol').setValue(user.rol);
      this.usernameTemporal = user.username;
      this.userValid = true;
      this.user = user;
    } else {
      this.usernameTemporal = '';
      this.updateUser = false;
      this.userValid = false;
      this.buttonSaveText = 'Guardar';
    }
  }

  save(notify: boolean = true) {

    if (this.userForm.invalid || !this.userValid) {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Los datos ingresados en el formulario estan incorrectos', life: 3000 });
      return;
    }
    const user: User = { ...this.userForm.getRawValue() };

    if (this.updateUser) {
      user.id = this.user.id;
      this.userService.update(user).subscribe(this.subscribeOnSaveUser(notify));
    }
    else
      this.userService.save(user).subscribe(this.subscribeOnSaveUser(notify));
  }


  getUserById(id: number): Promise<User> {
    return new Promise((resolve, reject) => {
      this.userService.getById(id).subscribe({
        next: (response) => {
          resolve(response.content);
        }, error: (error) => {
          resolve(null);
        }
      });
    });
  }

  validateUser() {
    if (this.updateUser && this.userForm.get('username').value.toString() === this.usernameTemporal) {
      this.userValid = true;
      return;
    }
    this.userService.exists(this.userForm.get('username').value).subscribe({
      next: (response) => {
        if (response.ok && response.content)
          this.userValid = false;
        else
          this.userValid = true;
        this.formUserValid.emit(this.userValid);
      }, error: (error) => {
        this.userValid = true;
        this.formUserValid.emit(this.userValid);
      },
    });
  }

  subscribeOnSaveUser(notify: boolean = true): Observer<Response<User>> {
    return {
      next: (response) => {
        if (response.ok) {
          this.userSaved.emit(response.content);
          if (notify)
            this.messageService.add({ severity: 'success', summary: 'Exito', detail: 'Usuario guardado correctamente', life: 3000 });
          if (this.disableSaveButton)
            this.router.navigate([this.preUrl.getPreviousUrl()]);
        }
        else {
          this.userSaved.emit(null);
          if (notify)
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible guardar el usuario', life: 3000 });
        }
      }, error: (error) => {
        this.userSaved.emit(null);
        if (notify)
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No fue posible guardar el usuario', life: 3000 });
      },
      complete: () => { }
    };
  }

}
