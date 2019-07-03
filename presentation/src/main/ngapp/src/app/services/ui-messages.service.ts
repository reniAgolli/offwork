import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';

@Injectable({
    providedIn: 'root'
})
export class UiMessagesService {

    constructor(private _toastrService: ToastrService) {
    }

    public showError(title: string, message: string): void {
        this.clearToasts();
        this._toastrService.error(message, title, null);
    }

    public showInfo(title: string, message: string): void {
        this.clearToasts();
        this._toastrService.info(message, title, null);
    }

    public showWarning(title: string, message: string): void {
        this.clearToasts();
        this._toastrService.warning(message, title, null);
    }

    public showSuccess(title: string, message: string): void {
        this.clearToasts();
        this._toastrService.success(message, title, null);
    }

    clearToasts(): void {
        this._toastrService.clear(this._toastrService.currentlyActive);
    }

}
