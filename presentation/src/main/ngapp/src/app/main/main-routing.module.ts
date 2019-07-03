import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserDetailsComponent} from './user-details/user-details.component';
import {LeavesComponent} from './leaves/leaves/leaves.component';

const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'leaves'},
    {path: 'user-details', component: UserDetailsComponent},
    {path: 'leaves', component: LeavesComponent}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MainRoutingModule {
}
