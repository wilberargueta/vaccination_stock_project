import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { SessionStorageService } from 'src/app/module/util/service/session-storage.service';
import { JwtToken } from '../../domain/jwt-token';
import { LoginService } from '../../service/login.service';
import * as moment from 'moment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class LoginComponent implements OnInit {
  spinner = true;

  loginForm: FormGroup = null;
  constructor(
    private loginService: LoginService,
    private sessionService: SessionStorageService,
    private messageService: MessageService,
    private confirmService: ConfirmationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl(null, [Validators.required, Validators.minLength(2)]),
      password: new FormControl(null, [Validators.required, Validators.minLength(5)])
    }, { updateOn: 'change' });
    this.spinner = false;
  }



  login() {
    this.spinner = true;
    this.loginService.getToken(this.loginForm.get('username').value, this.loginForm.get('password').value).subscribe({
      next: (response) => {
        if (response.ok) {
          this.sessionService.setValue<JwtToken>(response.content, 'session_token');
          this.sessionService.setValue<string>(moment().format('YYYY-MM-DD HH:mm:ss'), 'session_time');
          this.router.navigate(['/']);
        } else
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Credenciales invalidas', life: 3000 });

        this.spinner = false;
      }, error: (error) => {
        this.spinner = false;
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Credenciales invalidas', life: 3000 });
      }
    });
  }
}
