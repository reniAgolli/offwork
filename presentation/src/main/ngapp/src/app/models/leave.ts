import {User} from './user';

export interface Leave {

    id?: string;
    startDate: number;
    endDate: number;
    status?: 'PENDING' | 'CONFIRMED' | 'REVOKED';
    confirmedBy?: User;
    requestedBy?
        : User;
}
