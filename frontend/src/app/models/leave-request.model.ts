export type RequestStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface LeaveRequest {
  id?: number;
  startDate: string; // format ISO 'YYYY-MM-DD'
  endDate: string;
  reason?: string;
  requestDate?: string;   // rempli par le serveur
  status?: RequestStatus; // rempli par le serveur
  employeeId: number;
  employeeName?: string;  // rempli par le serveur
}