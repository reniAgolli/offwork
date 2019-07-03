import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {UiMessagesService} from '../../services/ui-messages.service';
import {Injectable} from '@angular/core';
import {AuthServiceService} from '../../services/auth-service.service';

@Injectable({
    providedIn: 'root'
})
export class ErrorHandlerInterceptor implements HttpInterceptor {

    constructor(private _messages: UiMessagesService,
                private _authService: AuthServiceService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request)
            .pipe(
                catchError((error: HttpErrorResponse) => {
                    this.onError(error);
                    return throwError(error);
                })
            );
    }

    private onError(error: HttpErrorResponse): void {
        if (error.error.status === 401) {
            this._authService.logout();
            return;
        }
        this._messages.showError(error.status + '', error.error.message);
    }
}
