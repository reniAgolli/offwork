import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {User} from '../models/user';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ResetPasswordDto} from '../models/reset-password-dto';

@Injectable({
    providedIn: 'root'
})
export class UserServiceService {

    private baseUri = `${environment.SERVER_URI}/api/users`;

    constructor(private http: HttpClient) {
    }

    create(user: User): Observable<User> {
        return this.http.post<User>(this.baseUri, user);
    }

    update(user: User): Observable<User> {
        return this.http.put<User>(this.baseUri, user);
    }

    delete(id: string): Observable<any> {
        return this.http.delete<any>(`${this.baseUri}/id`);
    }

    getAll(): Observable<User[]> {
        return this.http.get<User[]>(this.baseUri);
    }

    getById(id: string): Observable<User> {
        return this.http.get<User>(`${this.baseUri}/id`);
    }

    resetPassword(resetCredentials: ResetPasswordDto): Observable<User> {
        return this.http.post<User>(`${this.baseUri}/reset`, resetCredentials);
    }
}
