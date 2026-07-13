import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { LeaveRequest } from '../models/leave-request.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LeaveRequestService {

  private baseUrl = `${environment.apiUrl}/leave-requests`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<LeaveRequest[]> {
    return this.http.get<LeaveRequest[]>(this.baseUrl);
  }

  getById(id: number): Observable<LeaveRequest> {
    return this.http.get<LeaveRequest>(`${this.baseUrl}/${id}`);
  }

  getByEmployee(employeeId: number): Observable<LeaveRequest[]> {
    return this.http.get<LeaveRequest[]>(`${this.baseUrl}/employee/${employeeId}`);
  }

  create(request: LeaveRequest): Observable<LeaveRequest> {
    return this.http.post<LeaveRequest>(this.baseUrl, request);
  }

  approve(id: number): Observable<LeaveRequest> {
    return this.http.put<LeaveRequest>(`${this.baseUrl}/${id}/approve`, {});
  }

  reject(id: number): Observable<LeaveRequest> {
    return this.http.put<LeaveRequest>(`${this.baseUrl}/${id}/reject`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}