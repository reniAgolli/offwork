import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersComponent} from './users/users.component';
import {CuUsersComponent} from './cu-users/cu-users.component';

@NgModule({
    declarations: [UsersComponent, CuUsersComponent],
    imports: [
        CommonModule
    ],
    entryComponents: [CuUsersComponent]
})
export class UsersModule {
}
