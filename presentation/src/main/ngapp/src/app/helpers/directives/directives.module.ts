import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {hasRoleDirective} from "./has-role.directive";
import {TogglePasswordDirective} from "./toggle-password.directive";

@NgModule({
    declarations: [
        hasRoleDirective,
        TogglePasswordDirective
    ],
    imports: [
        CommonModule
    ],
    exports: [
        hasRoleDirective,
        TogglePasswordDirective
    ]
})
export class DirectivesModule {
}
