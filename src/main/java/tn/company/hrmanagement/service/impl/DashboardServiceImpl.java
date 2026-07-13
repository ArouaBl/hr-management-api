package tn.company.hrmanagement.service.impl;

import tn.company.hrmanagement.dto.DashboardDTO;
import tn.company.hrmanagement.dto.DepartmentEmployeeCountDTO;
import tn.company.hrmanagement.model.Department;
import tn.company.hrmanagement.model.RequestStatus;
import tn.company.hrmanagement.repository.AbsenceRequestRepository;
import tn.company.hrmanagement.repository.DepartmentRepository;
import tn.company.hrmanagement.repository.EmployeeRepository;
import tn.company.hrmanagement.repository.LeaveRequestRepository;
import tn.company.hrmanagement.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final AbsenceRequestRepository absenceRequestRepository;

    @Override
    public DashboardDTO getDashboard() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate firstDayOfMonth = currentMonth.atDay(1);
        LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();

        List<DepartmentEmployeeCountDTO> employeesByDepartment = departmentRepository.findAll()
                .stream()
                .map(this::toDepartmentCount)
                .collect(Collectors.toList());

        return new DashboardDTO(
                employeeRepository.count(),
                departmentRepository.count(),
                employeesByDepartment,

                leaveRequestRepository.countByStatus(RequestStatus.PENDING),
                leaveRequestRepository.countByStatus(RequestStatus.APPROVED),
                leaveRequestRepository.countByStatus(RequestStatus.REJECTED),
                leaveRequestRepository.countByStatusAndRequestDateBetween(
                        RequestStatus.APPROVED, firstDayOfMonth, lastDayOfMonth),

                absenceRequestRepository.countByStatus(RequestStatus.PENDING),
                absenceRequestRepository.countByStatus(RequestStatus.APPROVED),
                absenceRequestRepository.countByStatus(RequestStatus.REJECTED),
                absenceRequestRepository.countByStatusAndRequestDateBetween(
                        RequestStatus.APPROVED, firstDayOfMonth, lastDayOfMonth)
        );
    }

    private DepartmentEmployeeCountDTO toDepartmentCount(Department department) {
        long count = employeeRepository.countByDepartmentId(department.getId());
        return new DepartmentEmployeeCountDTO(department.getName(), count);
    }
}