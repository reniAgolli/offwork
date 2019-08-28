import {Leave} from './leave';

export interface User {
    id?: number;
    name?: string;
    surname?: string;
    username?: string;
    password?: string;
    email?: string;
    role?: 'ROLE_USER' | 'ROLE_ADMIN';
    applications?: Leave[];
    token?: string;
    leavesLeft?: number;

}