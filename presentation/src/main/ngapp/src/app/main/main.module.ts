import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MainRoutingModule} from './main-routing.module';
import {UserDetailsComponent} from './user-details/user-details.component';
import {LeavesModule} from './leaves/leaves.module';
import {SharedModule} from '../shared/shared.module';

@NgModule({
    declarations: [
        UserDetailsComponent
    ],
    imports: [
        CommonModule,
        MainRoutingModule,
        LeavesModule,
        SharedModule
    ]
})
export class MainModule {
}
