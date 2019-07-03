import {FormGroup, ValidationErrors} from '@angular/forms';
import * as moment from 'moment';

export class DateCompareValidator {

    static fromToValidator(group: FormGroup): ValidationErrors {
        const temp = group.getRawValue();
        const dateFrom = moment.isMoment(temp.dateFrom) ? temp.dateFrom.toDate().getTime() : temp.dateFrom.getTime();
        const dateTo = moment.isMoment(temp.dateTo) ? temp.dateTo.toDate().getTime() : temp.dateTo.getTime();
        return dateFrom < dateTo ? null : DateCompareValidator.onMisMatch(group);
    }

    private static onMisMatch(group: FormGroup): ValidationErrors {
        const error: ValidationErrors = {mismatch: true};
        group.get('dateTo').setErrors(error);
        return error;
    }
}
