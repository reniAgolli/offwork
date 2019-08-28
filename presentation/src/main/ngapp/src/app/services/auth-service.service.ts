import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {LoginCredentials} from '../models/login-credentials';
import {tap} from 'rxjs/operators';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';
import {UserServiceService} from "./user-service.service";

@Injectable({
    providedIn: 'root'
})
export class AuthServiceService {

    public static USER_LOCALSTORAGE = 'CURRENT_USER';
    public static TOKEN_COOKIE = 'AUTH_TOKEN';

    private baseUri = environment.SERVER_URI;

    constructor(private http: HttpClient,
                private cookieService: CookieService,
                private _user: UserServiceService,
                private _router: Router) {
    }

    get currentUser(): User {
        return JSON.parse(localStorage.getItem(AuthServiceService.USER_LOCALSTORAGE));
    }

    static hasRole(role: string): boolean {
        const currentUser = JSON.parse(localStorage.getItem(AuthServiceService.USER_LOCALSTORAGE));
        return !!currentUser && currentUser.role === role;
    }

    static hasAnyRole(roles: string[]): boolean {
        const currentUser = JSON.parse(localStorage.getItem(AuthServiceService.USER_LOCALSTORAGE));
        return !!currentUser && roles.indexOf(currentUser.role) >= 0;
    }

    authenticate(loginCredentials: LoginCredentials): Observable<User> {
        return this.http.post<User>(`${this.baseUri}/login`, loginCredentials)
            .pipe(tap(user => this.setCurrentUser(user)));
    }

    logout(): void {
        this.http.get<any>(`${this.baseUri}/logout`)
        // .pipe(tap(e => this.invalidateToken()))
            .subscribe(e => this.invalidateToken());
    }

    isAuthenticated(): boolean {
        return !!this.cookieService.get(AuthServiceService.TOKEN_COOKIE);
    }

    getToken(): string {
        return this.cookieService.get(AuthServiceService.TOKEN_COOKIE);
    }

    private setCurrentUser(user: User): void {
        this.cookieService.set(AuthServiceService.TOKEN_COOKIE, user.token);
        localStorage.setItem(AuthServiceService.USER_LOCALSTORAGE, JSON.stringify(user));
    }


    private invalidateToken(): void {
        localStorage.removeItem(AuthServiceService.USER_LOCALSTORAGE);
        this.cookieService.delete(AuthServiceService.TOKEN_COOKIE);
        this._router.navigateByUrl('/login');
    }
}
