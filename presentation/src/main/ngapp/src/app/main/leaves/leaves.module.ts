import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LeavesComponent} from './leaves/leaves.component';
import {LeavesTableViewComponent} from './leaves-table-view/leaves-table-view.component';
import {LeavesCalendarViewComponent} from './leaves-calendar-view/leaves-calendar-view.component';
import {CuLeavesComponent} from './cu-leaves/cu-leaves.component';
import {SharedModule} from '../../shared/shared.module';

@NgModule({
    declarations: [
        LeavesComponent,
        LeavesTableViewComponent,
        LeavesCalendarViewComponent,
        CuLeavesComponent
    ],
    imports: [
        CommonModule,
        SharedModule
    ],
    entryComponents: [
        CuLeavesComponent
    ]
})
export class LeavesModule {
}
