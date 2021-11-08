import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { PrimeNGConfig } from 'primeng/api';
import { PerviousRouterService } from './module/util/service/previous-router.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  topbarTheme: string = 'blue';

  menuTheme: string = 'light';

  layoutMode: string = 'light';

  menuMode: string = 'static';

  inlineMenuPosition: string = 'bottom';

  inputStyle: string = 'filled';

  ripple: boolean = true;

  isRTL: boolean = false;


  constructor(
    private primengConfig: PrimeNGConfig,
    private previousRouterService: PerviousRouterService,
    private router:Router
  ) {
    this.router.events.subscribe(event =>{
        if(event instanceof NavigationStart)
          console.log(event.url);
    })
   }

  ngOnInit() {
    this.primengConfig.ripple = true;
  }
}
