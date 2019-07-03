import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthServiceService} from '../../services/auth-service.service';

@Injectable({
    providedIn: 'root'
})
export class AdminGuardGuard implements CanActivate, CanActivateChild {

    constructor(private _authService: AuthServiceService,
                private _router: Router) {
    }

    canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if (!this._authService.hasRole('ADMIN')) {
            this._router.navigateByUrl('/login');
            return false;
        }
        return true;
    }

    canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if (!this._authService.hasRole('ADMIN')) {
            this._router.navigateByUrl('/login');
            return false;
        }
        return true;
    }
}
