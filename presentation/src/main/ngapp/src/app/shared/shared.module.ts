import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
    FuseConfirmDialogModule,
    FuseProgressBarModule,
    FuseSidebarModule,
    FuseThemeOptionsModule
} from '../../@fuse/components';
import {FuseSharedModule} from '../../@fuse/shared.module';
import {MaterialModule} from '../material/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ResetPasswordComponent} from './components/reset-password/reset-password.component';
import {CuUsersComponent} from "./components/cu-users/cu-users.component";
import {DirectivesModule} from "../helpers/directives/directives.module";

@NgModule({
    declarations: [
        ResetPasswordComponent,
        CuUsersComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        FuseSharedModule,
        MaterialModule,
        FuseConfirmDialogModule,
        DirectivesModule
    ],
    exports: [
        FormsModule,
        ReactiveFormsModule,
        FuseProgressBarModule,
        FuseSharedModule,
        FuseSidebarModule,
        FuseThemeOptionsModule,
        FuseConfirmDialogModule,
        ResetPasswordComponent,
        MaterialModule,
        DirectivesModule
    ],
    entryComponents: [ResetPasswordComponent, CuUsersComponent]
})
export class SharedModule {
}
