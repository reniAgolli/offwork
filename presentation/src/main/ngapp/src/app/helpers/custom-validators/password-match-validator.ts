import {FormGroup, ValidationErrors} from '@angular/forms';

export class PasswordMatchValidator {

    static passwordMatchValidator(group: FormGroup): ValidationErrors {
        return group.get('password').value === group.get('repPassword').value ? null : PasswordMatchValidator.onMisMatch(group);
    }

    private static onMisMatch(group: FormGroup): ValidationErrors {
        const error: ValidationErrors = {mismatch: true};
        group.get('repPassword').setErrors(error);
        return error;
    }
}
