import { Component, ElementRef, ViewChild } from '@angular/core';
import { trigger, style, transition, animate, AnimationEvent } from '@angular/animations';
import { MegaMenuItem } from 'primeng/api';
import { AppComponent } from 'src/app/app.component';
import { Router } from '@angular/router';
import { MainComponent } from '../main/main.component';
import { SessionStorageService } from '../../util/service/session-storage.service';


@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html',
    animations: [
        trigger('topbarActionPanelAnimation', [
            transition(':enter', [
                style({ opacity: 0, transform: 'scaleY(0.8)' }),
                animate('.12s cubic-bezier(0, 0, 0.2, 1)', style({ opacity: 1, transform: '*' })),
            ]),
            transition(':leave', [
                animate('.1s linear', style({ opacity: 0 }))
            ])
        ])
    ]
})
export class AppTopBarComponent {

    constructor(public appMain: MainComponent,
        public app: AppComponent,
        private sessionService: SessionStorageService,
        public router: Router) {
    }

    activeItem: number;

    model: MegaMenuItem[] = [
        {
            label: 'UI KIT',
            items: [
                [
                    {
                        label: 'UI KIT 1',
                        items: [
                            { label: 'Form Layout', icon: 'pi pi-fw pi-id-card', routerLink: ['/uikit/formlayout'] },

                        ]
                    }
                ],
                [
                    {
                        label: 'UI KIT 2',
                        items: [
                            { label: 'Table', icon: 'pi pi-fw pi-table', routerLink: ['/uikit/table'] },
                            { label: 'List', icon: 'pi pi-fw pi-list', routerLink: ['/uikit/list'] },

                        ]
                    }
                ],

            ]
        },
        {
            label: 'UTILITIES',
            items: [
                [
                    {
                        label: 'UTILITIES 1',
                        items: [
                            { label: 'Display', icon: 'pi pi-fw pi-desktop', routerLink: ['utilities/display'] },

                        ]
                    },
                    {
                        label: 'UTILITIES 2',
                        items: [
                            { label: 'FlexBox', icon: 'pi pi-fw pi-directions', routerLink: ['utilities/flexbox'] }
                        ]
                    }
                ],
                [
                    {
                        label: 'UTILITIES 3',
                        items: [
                            { label: 'Icons', icon: 'pi pi-fw pi-search', routerLink: ['utilities/icons'] }
                        ]
                    },

                ],

            ]
        }
    ];

    @ViewChild('searchInput') searchInputViewChild: ElementRef;

    onSearchAnimationEnd(event: AnimationEvent) {
        switch (event.toState) {
            case 'visible':
                this.searchInputViewChild.nativeElement.focus();
                break;
        }
    }

    logout() {
        this.sessionService.delete('session_token');
        this.sessionService.delete('session_time');
        this.router.navigate(['/login']);
    }
}
