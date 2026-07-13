export interface DepartmentEmployeeCount {
  departmentName: string;
  employeeCount: number;
}

export interface Dashboard {
  totalEmployees: number;
  totalDepartments: number;
  employeesByDepartment: DepartmentEmployeeCount[];

  pendingLeaveRequests: number;
  approvedLeaveRequests: number;
  rejectedLeaveRequests: number;
  leaveRequestsApprovedThisMonth: number;

  pendingAbsenceRequests: number;
  approvedAbsenceRequests: number;
  rejectedAbsenceRequests: number;
  absenceRequestsApprovedThisMonth: number;
}