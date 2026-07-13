export interface Employee {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
  address?: string;
  position?: string;
  hireDate?: string; // format ISO 'YYYY-MM-DD', compatible avec <input type="date">
  salary?: number;
  departmentId: number;
  departmentName?: string; // rempli par le serveur, lecture seule
}