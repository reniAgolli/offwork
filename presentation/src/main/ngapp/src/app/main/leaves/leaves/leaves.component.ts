import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {LeavesServiceService} from '../../../services/leaves-service.service';
import {MatDialog, MatPaginator, MatTableDataSource} from '@angular/material';
import {filter, flatMap, tap} from 'rxjs/operators';
import {Leave} from '../../../models/leave';
import {CuLeavesComponent} from '../cu-leaves/cu-leaves.component';
import {FuseConfirmDialogComponent} from '../../../../@fuse/components/confirm-dialog/confirm-dialog.component';

@Component({
    selector: 'app-leaves',
    templateUrl: './leaves.component.html',
    styleUrls: ['./leaves.component.scss']
})
export class LeavesComponent implements OnInit, AfterViewInit {
    public dataSource: MatTableDataSource<Leave> = new MatTableDataSource();
    public displayedColumns: string[] = ['start', 'end', 'status', 'requested', 'confirmed', 'actions'];
    public pageSizeOptions: number[] = [5, 10, 20, 50];
    @ViewChild(MatPaginator) private paginator;

    constructor(private _leavesService: LeavesServiceService,
                private _dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.getAllLeaves();
    }

    getAllLeaves(): void {
        this._leavesService.getAll()
            .pipe(tap(leaves => this.dataSource.data = leaves))
            .subscribe();
    }

    cULeave(leave?: Leave): void {
        this._dialog.open(CuLeavesComponent, {data: leave ? leave : {}})
            .afterClosed().pipe(
            filter(e => e),
            tap(e => this.getAllLeaves()))
            .subscribe();
    }

    deleteLeave(id: string): void {
        this._dialog.open(FuseConfirmDialogComponent)
            .afterClosed().pipe(
            filter(e => e),
            flatMap(e => this._leavesService.delete(id)),
            tap(e => this.getAllLeaves()))
            .subscribe();
    }

    confirmLeave(id: string): void {
        this._leavesService.confirm(id)
            .subscribe(e => this.getAllLeaves());
    }

    revokeLeave(id: string): void {
        this._leavesService.revoke(id)
            .subscribe(e => this.getAllLeaves());
    }

    ngAfterViewInit(): void {
        this.dataSource.paginator = this.paginator;
    }

}
