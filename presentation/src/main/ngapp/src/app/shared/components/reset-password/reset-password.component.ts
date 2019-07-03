import {Component, Inject, OnInit} from '@angular/core';
import {UserServiceService} from '../../../services/user-service.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {PasswordMatchValidator} from '../../../helpers/custom-validators/password-match-validator';
import {ValidateFn} from 'codelyzer/walkerFactory/walkerFn';

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

    formGroup: FormGroup;

    constructor(private _userService: UserServiceService,
                private _dialogRef: MatDialogRef<ResetPasswordComponent>,
                @Inject(MAT_DIALOG_DATA) private _userId: { id: string }) {
    }

    ngOnInit(): void {
        this.initForm();
    }

    save(): void {
        const temp = this.formGroup.getRawValue();
        this._userService.resetPassword({userId: this._userId.id, newPassword: temp.password})
            .subscribe(e => this.close(true));
    }

    close(result: boolean): void {
        this._dialogRef.close(result);
    }

    private initForm(): void {
        this.formGroup = new FormGroup({
            password: new FormControl('', [Validators.required, Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$/)]),
            repPassword: new FormControl('', Validators.required)
        }, {validators: PasswordMatchValidator.passwordMatchValidator});
    }
}
