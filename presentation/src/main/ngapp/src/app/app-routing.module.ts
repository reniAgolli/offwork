import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AuthGuardGuard} from './helpers/guards/auth-guard.guard';
import {AdminGuardGuard} from './helpers/guards/admin-guard.guard';

const routes: Routes = [
    {
        path: '',
        loadChildren: './main/main.module#MainModule',
        canActivate: [AuthGuardGuard],
        canActivateChild: [AuthGuardGuard]
    },
    {
        path: 'admin',
        loadChildren: './admin/admin.module#AdminModule#AdminModule',
        // canActivate: [AuthGuardGuard, AdminGuardGuard],
        // canActivateChild: [AuthGuardGuard, AdminGuardGuard]
    },
    {path: 'login', component: LoginComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
