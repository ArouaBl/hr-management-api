package tn.company.hrmanagement.service;

import tn.company.hrmanagement.dto.AbsenceRequestDTO;

import java.util.List;

public interface AbsenceRequestService {

    AbsenceRequestDTO createAbsenceRequest(AbsenceRequestDTO dto);

    AbsenceRequestDTO approveAbsenceRequest(Long id);

    AbsenceRequestDTO rejectAbsenceRequest(Long id);

    void deleteAbsenceRequest(Long id);

    AbsenceRequestDTO getAbsenceRequestById(Long id);

    List<AbsenceRequestDTO> getAllAbsenceRequests();

    List<AbsenceRequestDTO> getAbsenceRequestsByEmployee(Long employeeId);
}