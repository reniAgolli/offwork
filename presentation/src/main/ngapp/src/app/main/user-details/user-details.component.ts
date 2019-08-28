import {Component, OnInit} from '@angular/core';
import {User} from "../../models/user";
import {AuthServiceService} from "../../services/auth-service.service";
import {MatDialog} from "@angular/material";
import {CuUsersComponent} from "../../shared/components/cu-users/cu-users.component";
import {filter, tap} from "rxjs/operators";
import {ResetPasswordComponent} from "../../shared/components/reset-password/reset-password.component";
import {UserServiceService} from "../../services/user-service.service";

@Component({
    selector: 'app-user-details',
    templateUrl: './user-details.component.html',
    styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

    currentUser: User;

    constructor(private _dialog: MatDialog,
                private _service: UserServiceService) {
    }

    private static updateCurrentUser(user: User) {
        localStorage.setItem(AuthServiceService.USER_LOCALSTORAGE, JSON.stringify(user));
    }

    ngOnInit() {
        this.getCurrenUser()
    }

    updateProfile() {
        this._dialog.open(CuUsersComponent, {data: this.currentUser})
            .afterClosed().pipe(
            filter(e => e),
            tap(e => UserDetailsComponent.updateCurrentUser(e)))
            .subscribe()
    }

    updatePassword() {
        this._dialog.open(ResetPasswordComponent, {data: {id: this.currentUser.id}})
            .afterClosed().pipe(
            filter(e => e),
            tap(UserDetailsComponent.updateCurrentUser))
            .subscribe()
    }

    private getCurrenUser(): void {
        this.currentUser = JSON.parse(localStorage.getItem(AuthServiceService.USER_LOCALSTORAGE));
        this._service.getById(this.currentUser.id).subscribe(e => this.currentUser = e)
    }
}
