import { Component, Input, OnInit } from '@angular/core';
import { trigger, state, transition, style, animate } from '@angular/animations';
import { AppComponent } from 'src/app/app.component';
import { Router } from '@angular/router';
import { MainComponent } from '../main/main.component';

@Component({
  selector: 'app-inline-menu',
  templateUrl: './inline-menu.component.html',
  styleUrls: ['./inline-menu.component.scss'],
  animations: [
    trigger('menu', [
        state('hiddenAnimated', style({
            height: '0px',
            paddingBottom: '0px',
            overflow: 'hidden'
        })),
        state('visibleAnimated', style({
            height: '*',
            overflow: 'visible'
        })),
        state('visible', style({
            opacity: 1,
            'z-index': 100
        })),
        state('hidden', style({
            opacity: 0,
            'z-index': '*'
        })),
        transition('visibleAnimated => hiddenAnimated', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
        transition('hiddenAnimated => visibleAnimated', animate('400ms cubic-bezier(0.86, 0, 0.07, 1)')),
        transition('visible => hidden', animate('.1s linear')),
        transition('hidden => visible', [style({ transform: 'scaleY(0.8)' }), animate('.12s cubic-bezier(0, 0, 0.2, 1)')])
    ])
]
})
export class InlineMenuComponent implements OnInit {


  @Input() key = "inline-menu";

  @Input() style: any;

  @Input() styleClass: string;

  active: boolean;

  // people: People;

  constructor(public appMain: MainComponent,
      public app: AppComponent,
    //   public authService: AuthService,
      // public peopleService:PeopleService,
      public router: Router) { }

  ngOnInit() {
      // this.peopleService.getById(this.authService.getJWT().id).subscribe(response =>{
      //  if(response.message.status === 'SUCCESS'){
      //     this.people = response.data;
      // }
      // },error =>{

      // })

  }

  onClick(event) {
      this.appMain.onInlineMenuClick(event, this.key);
      event.preventDefault();
  }

//   logout() {
//       this.authService.logout();
//       sessionStorage.removeItem('session_user');
//       this.router.navigate(['/login']);
//   }

  get isTooltipDisabled() {
      return !(this.appMain.isSlim() && !this.appMain.isMobile());
  }

  get tabIndex() {
      return !this.appMain.inlineMenuActive ? '-1' : null;
  }

  isHorizontalActive() {
      return this.appMain.isHorizontal() && !this.appMain.isMobile();
  }


}
