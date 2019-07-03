import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FuseConfirmDialogModule, FuseProgressBarModule, FuseSidebarModule, FuseThemeOptionsModule} from '../../@fuse/components';
import {FuseSharedModule} from '../../@fuse/shared.module';
import {MaterialModule} from '../material/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ResetPasswordComponent} from './components/reset-password/reset-password.component';
import {TogglePasswordDirective} from '../helpers/directives/toggle-password.directive';

@NgModule({
    declarations: [
        ResetPasswordComponent,
        TogglePasswordDirective
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        FuseSharedModule,
        MaterialModule,
        FuseConfirmDialogModule
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
        TogglePasswordDirective
    ],
    entryComponents: [ResetPasswordComponent]
})
export class SharedModule {
}
