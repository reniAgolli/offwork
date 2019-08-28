import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatPaginator, MatTableDataSource} from '@angular/material';
import {User} from '../../../models/user';
import {UserServiceService} from '../../../services/user-service.service';
import {filter, tap} from 'rxjs/operators';
import {CuUsersComponent} from '../../../shared/components/cu-users/cu-users.component';
import {FuseConfirmDialogComponent} from '../../../../@fuse/components/confirm-dialog/confirm-dialog.component';
import {ResetPasswordComponent} from '../../../shared/components/reset-password/reset-password.component';

@Component({
    selector: 'app-users',
    templateUrl: './users.component.html',
    styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit, AfterViewInit {

    public dataSource: MatTableDataSource<User> = new MatTableDataSource();
    public displayedColumns: string[] = ['name', 'surname', 'username', 'email', 'leaves', 'actions'];
    public pageSizeOptions: number[] = [5, 10, 20, 50];
    @ViewChild(MatPaginator) private paginator;

    constructor(private _userService: UserServiceService,
                private _dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.getAllUsers();
    }

    getAllUsers(): void {
        this._userService.getAll()
            .pipe(tap(users => this.dataSource.data = users))
            .subscribe();
    }

    ngAfterViewInit(): void {
        this.dataSource.paginator = this.paginator;
    }

    cUUser(user?: User): void {
        this._dialog.open(CuUsersComponent, {data: user})
            .afterClosed().pipe(
            filter(e => e),
            tap(e => this.getAllUsers()))
            .subscribe();
    }

    resetPassword(id: string): void {
        this._dialog.open(ResetPasswordComponent, {data: {id}});
    }

    deleteUser(id: string): void {
        this._dialog.open(FuseConfirmDialogComponent)
            .afterClosed().pipe(
            filter(e => e),
            tap(e => this.getAllUsers()))
            .subscribe();
    }
}
