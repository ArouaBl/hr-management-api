package tn.company.hrmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    private long totalEmployees;
    private long totalDepartments;
    private List<DepartmentEmployeeCountDTO> employeesByDepartment;

    private long pendingLeaveRequests;
    private long approvedLeaveRequests;
    private long rejectedLeaveRequests;
    private long leaveRequestsApprovedThisMonth;

    private long pendingAbsenceRequests;
    private long approvedAbsenceRequests;
    private long rejectedAbsenceRequests;
    private long absenceRequestsApprovedThisMonth;
}