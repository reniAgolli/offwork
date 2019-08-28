import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserServiceService} from '../../../services/user-service.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {User} from '../../../models/user';
import {PasswordMatchValidator} from '../../../helpers/custom-validators/password-match-validator';

@Component({
    selector: 'app-cu-users',
    templateUrl: './cu-users.component.html',
    styleUrls: ['./cu-users.component.scss']
})
export class CuUsersComponent implements OnInit {

    formGroup: FormGroup;
    message: string;
    private isUpdate: boolean;

    constructor(private _userService: UserServiceService,
                private _dialogRef: MatDialogRef<CuUsersComponent>,
                @Inject(MAT_DIALOG_DATA) private data: User) {
    }

    ngOnInit(): void {
        this.isUpdate = !!this.data.id;
        this.initData();
        this.iniForm();
    }

    save(): void {
        this.collectData();
        const sub = !this.isUpdate ? this._userService.create(this.data) : this._userService.update(this.data);
        sub.subscribe(e => this.close(this.data));
    }

    close(result?: boolean | User): void {
        this._dialogRef.close(result);
    }

    private initData(): void {
        this.data = this.isUpdate ? Object.assign({}, this.data) : {
            name: '',
            surname: '',
            username: '',
            email: '',
            password: '',
            role: 'ROLE_USER'
        };
        const tit = !this.isUpdate ? 'Create new' : 'Update';
        this.message = `${tit} User`;
    }

    private iniForm(): void {
        this.formGroup = new FormGroup({
            name: new FormControl(this.data.name, [Validators.required, Validators.minLength(3)]),
            surname: new FormControl(this.data.surname, [Validators.required, Validators.minLength(3)]),
            username: new FormControl(this.data.username, [Validators.required, Validators.minLength(3)]),
            email: new FormControl(this.data.email, [Validators.required, Validators.email]),
            role: new FormControl(this.data.role, [Validators.required]),
        });

        if (!this.isUpdate) {
            this.formGroup.addControl('password', new FormControl(this.data.password,
                [Validators.required, Validators.pattern(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$/)]));
            this.formGroup.addControl('repPassword', new FormControl(this.data.password, [Validators.required]));
            this.formGroup.setValidators(PasswordMatchValidator.passwordMatchValidator);
        }
    }

    private collectData(): void {
        const temp = this.formGroup.getRawValue();
        this.data.name = temp.name;
        this.data.username = temp.username;
        this.data.surname = temp.surname;
        this.data.email = temp.email;
        this.data.role = temp.role;
        this.data.password = temp.password;
    }
}
