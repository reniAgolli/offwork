import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LeavesServiceService} from '../../../services/leaves-service.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Leave} from '../../../models/leave';
import {DateCompareValidator} from '../../../helpers/custom-validators/date-compare-validator';

@Component({
    selector: 'app-cu-leaves',
    templateUrl: './cu-leaves.component.html',
    styleUrls: ['./cu-leaves.component.scss']
})
export class CuLeavesComponent implements OnInit {

    formGroup: FormGroup;
    message: string;
    isUpdate: boolean;
    minStartDate = new Date();
    minEndDate = new Date(new Date().getTime() + 24 * 3600 * 1000);


    constructor(private _leavesService: LeavesServiceService,
                private _dialogRef: MatDialogRef<CuLeavesComponent>,
                @Inject(MAT_DIALOG_DATA) private data: Leave) {
    }

    ngOnInit(): void {
        this.isUpdate = !!this.data.id;
        this.initData();
        this.initForm();
    }

    initData(): void {
        this.data = this.isUpdate ? this.data : {
            startDate: this.minStartDate.getTime(),
            endDate: this.minEndDate.getTime()
        };
        const tit = !this.isUpdate ? 'Create new' : 'Update';
        this.message = `${tit} Leave`;
    }

    initForm(): void {
        this.formGroup = new FormGroup({
            dateFrom: new FormControl(new Date(this.data.startDate), [Validators.required]),
            dateTo: new FormControl(new Date(this.data.endDate), Validators.required)
        }, {validators: DateCompareValidator.fromToValidator});
    }

    save(): void {
        const temp = this.formGroup.getRawValue();
        const sub = !this.isUpdate ? this._leavesService.create(this.data) : this._leavesService.update(this.data);
        this.data.startDate = temp.dateFrom.toDate().getTime();
        this.data.endDate = temp.dateTo.toDate().getTime();
        sub.subscribe(e => this.close(true));
    }

    close(result?: boolean): void {
        this._dialogRef.close(result);
    }
}

