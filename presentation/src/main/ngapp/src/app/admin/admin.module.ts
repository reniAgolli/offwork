import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdminRoutingModule} from './admin-routing.module';
import {SharedModule} from '../shared/shared.module';
import {UsersModule} from "./users/users.module";

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        AdminRoutingModule,
        SharedModule,
        UsersModule
    ],
    entryComponents: []
})
export class AdminModule {
}
