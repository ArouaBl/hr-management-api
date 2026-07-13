package tn.company.hrmanagement.service;

import tn.company.hrmanagement.dto.LeaveRequestDTO;

import java.util.List;

public interface LeaveRequestService {

    LeaveRequestDTO createLeaveRequest(LeaveRequestDTO dto);

    LeaveRequestDTO approveLeaveRequest(Long id);

    LeaveRequestDTO rejectLeaveRequest(Long id);

    void deleteLeaveRequest(Long id);

    LeaveRequestDTO getLeaveRequestById(Long id);

    List<LeaveRequestDTO> getAllLeaveRequests();

    List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId);
}