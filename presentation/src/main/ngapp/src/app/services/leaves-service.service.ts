import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Leave} from '../models/leave';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class LeavesServiceService {

    private baseUri = `${environment.SERVER_URI}/api/application`;

    constructor(private http: HttpClient) {
    }

    create(leave: Leave): Observable<Leave> {
        return this.http.post<Leave>(this.baseUri, leave);
    }

    update(leave: Leave): Observable<Leave> {
        return this.http.put<Leave>(this.baseUri, leave);
    }

    delete(id: string): Observable<any> {
        return this.http.delete<any>(`${this.baseUri}/${id}`);
    }

    getAll(): Observable<Leave[]> {
        return this.http.get<Leave[]>(this.baseUri);
    }

    getById(id: string): Observable<Leave> {
        return this.http.get<Leave>(`${this.baseUri}/${id}`);
    }

    confirm(id: string): Observable<Leave> {
        return this.http.put<Leave>(`${this.baseUri}/confirm/${id}`, null);
    }

    revoke(id: string): Observable<Leave> {
        return this.http.put<Leave>(`${this.baseUri}/revoke/${id}`, null);
    }
}
