import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AbsenceRequest } from '../models/absence-request.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AbsenceRequestService {

  private baseUrl = `${environment.apiUrl}/absence-requests`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<AbsenceRequest[]> {
    return this.http.get<AbsenceRequest[]>(this.baseUrl);
  }

  getById(id: number): Observable<AbsenceRequest> {
    return this.http.get<AbsenceRequest>(`${this.baseUrl}/${id}`);
  }

  getByEmployee(employeeId: number): Observable<AbsenceRequest[]> {
    return this.http.get<AbsenceRequest[]>(`${this.baseUrl}/employee/${employeeId}`);
  }

  create(request: AbsenceRequest): Observable<AbsenceRequest> {
    return this.http.post<AbsenceRequest>(this.baseUrl, request);
  }

  approve(id: number): Observable<AbsenceRequest> {
    return this.http.put<AbsenceRequest>(`${this.baseUrl}/${id}/approve`, {});
  }

  reject(id: number): Observable<AbsenceRequest> {
    return this.http.put<AbsenceRequest>(`${this.baseUrl}/${id}/reject`, {});
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}