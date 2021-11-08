import {Component} from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { MainComponent } from '../main/main.component';


@Component({
    selector: 'app-rightmenu',
    templateUrl: './app.rightmenu.component.html'
})
export class AppRightMenuComponent {
    constructor(public appMain: MainComponent, public app: AppComponent) {}
}
