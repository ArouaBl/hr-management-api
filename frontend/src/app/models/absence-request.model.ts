export type RequestStatus = 'PENDING' | 'APPROVED' | 'REJECTED';

export interface AbsenceRequest {
  id?: number;
  absenceDate: string; // format ISO 'YYYY-MM-DD'
  startTime: string;   // format 'HH:mm'
  endTime: string;     // format 'HH:mm'
  reason?: string;
  requestDate?: string;   // rempli par le serveur
  status?: RequestStatus; // rempli par le serveur
  employeeId: number;
  employeeName?: string;  // rempli par le serveur
}