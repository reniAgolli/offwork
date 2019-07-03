import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginCredentials} from '../models/login-credentials';
import {AuthServiceService} from '../services/auth-service.service';
import {Router} from '@angular/router';
import {FuseConfigService} from '../../@fuse/services/config.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loginCredentials: LoginCredentials = {
        emailOrUsername: '',
        password: ''
    };

    constructor(private _authService: AuthServiceService,
                private _fuseConfigService: FuseConfigService,
                private _router: Router) {
        if (this._authService.isAuthenticated()) {
            this._router.navigateByUrl('/');
        }

        this._fuseConfigService.config = {
            layout: {
                navbar: {
                    hidden: true
                },
                toolbar: {
                    hidden: true
                },
                footer: {
                    hidden: true
                },
                sidepanel: {
                    hidden: true
                }
            }
        };
    }

    ngOnInit(): void {
        this.initForm();
    }

    authenticate(): void {
        this.loginCredentials = this.loginForm.getRawValue();
        this._authService.authenticate(this.loginCredentials)
            .subscribe(
                succes => this.onAuthSuccess(),
                error => this.onAuthError(error));
    }

    private initForm(): void {
        this.loginForm = new FormGroup({
            emailOrUsername: new FormControl(this.loginCredentials.emailOrUsername, Validators.required),
            password: new FormControl(this.loginCredentials.password, Validators.required)
        });
    }

    private onAuthSuccess(): void {
        this._router.navigateByUrl('/');
    }

    private onAuthError(error: any): void {
        console.log(error);
    }
}
