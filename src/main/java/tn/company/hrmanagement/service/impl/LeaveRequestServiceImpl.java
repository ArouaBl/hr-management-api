package tn.company.hrmanagement.service.impl;

import tn.company.hrmanagement.dto.LeaveRequestDTO;
import tn.company.hrmanagement.exception.InvalidRequestException;
import tn.company.hrmanagement.exception.ResourceNotFoundException;
import tn.company.hrmanagement.model.Employee;
import tn.company.hrmanagement.model.LeaveRequest;
import tn.company.hrmanagement.model.RequestStatus;
import tn.company.hrmanagement.repository.EmployeeRepository;
import tn.company.hrmanagement.repository.LeaveRequestRepository;
import tn.company.hrmanagement.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public LeaveRequestDTO createLeaveRequest(LeaveRequestDTO dto) {
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidRequestException("La date de fin ne peut pas etre avant la date de debut");
        }

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employe introuvable avec l'id : " + dto.getEmployeeId()));

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setRequestDate(LocalDate.now());
        leaveRequest.setStatus(RequestStatus.PENDING);
        leaveRequest.setEmployee(employee);

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        return toDTO(saved);
    }

    @Override
    public LeaveRequestDTO approveLeaveRequest(Long id) {
        LeaveRequest leaveRequest = findOrThrow(id);
        assertPending(leaveRequest);
        leaveRequest.setStatus(RequestStatus.APPROVED);
        return toDTO(leaveRequestRepository.save(leaveRequest));
    }

    @Override
    public LeaveRequestDTO rejectLeaveRequest(Long id) {
        LeaveRequest leaveRequest = findOrThrow(id);
        assertPending(leaveRequest);
        leaveRequest.setStatus(RequestStatus.REJECTED);
        return toDTO(leaveRequestRepository.save(leaveRequest));
    }

    @Override
    public void deleteLeaveRequest(Long id) {
        LeaveRequest leaveRequest = findOrThrow(id);
        if (leaveRequest.getStatus() != RequestStatus.PENDING) {
            throw new InvalidRequestException("Seule une demande en attente peut etre annulee");
        }
        leaveRequestRepository.deleteById(id);
    }

    @Override
    public LeaveRequestDTO getLeaveRequestById(Long id) {
        return toDTO(findOrThrow(id));
    }

    @Override
    public List<LeaveRequestDTO> getAllLeaveRequests() {
        return leaveRequestRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> getLeaveRequestsByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employe introuvable avec l'id : " + employeeId);
        }
        return leaveRequestRepository.findByEmployeeIdOrderByRequestDateDesc(employeeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private LeaveRequest findOrThrow(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Demande de conge introuvable avec l'id : " + id));
    }

    private void assertPending(LeaveRequest leaveRequest) {
        if (leaveRequest.getStatus() != RequestStatus.PENDING) {
            throw new InvalidRequestException(
                    "Cette demande a deja ete traitee (statut actuel : " + leaveRequest.getStatus() + ")");
        }
    }

    // --- Conversion manuelle Entity -> DTO ---

    private LeaveRequestDTO toDTO(LeaveRequest leaveRequest) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(leaveRequest.getId());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setReason(leaveRequest.getReason());
        dto.setRequestDate(leaveRequest.getRequestDate());
        dto.setStatus(leaveRequest.getStatus());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setEmployeeName(
                leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName());
        return dto;
    }
}