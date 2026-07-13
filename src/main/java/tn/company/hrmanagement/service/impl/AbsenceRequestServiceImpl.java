package tn.company.hrmanagement.service.impl;

import tn.company.hrmanagement.dto.AbsenceRequestDTO;
import tn.company.hrmanagement.exception.InvalidRequestException;
import tn.company.hrmanagement.exception.ResourceNotFoundException;
import tn.company.hrmanagement.model.AbsenceRequest;
import tn.company.hrmanagement.model.Employee;
import tn.company.hrmanagement.model.RequestStatus;
import tn.company.hrmanagement.repository.AbsenceRequestRepository;
import tn.company.hrmanagement.repository.EmployeeRepository;
import tn.company.hrmanagement.service.AbsenceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbsenceRequestServiceImpl implements AbsenceRequestService {

    private final AbsenceRequestRepository absenceRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public AbsenceRequestDTO createAbsenceRequest(AbsenceRequestDTO dto) {
        if (!dto.getEndTime().isAfter(dto.getStartTime())) {
            throw new InvalidRequestException("L'heure de fin doit etre apres l'heure de debut");
        }

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employe introuvable avec l'id : " + dto.getEmployeeId()));

        AbsenceRequest absenceRequest = new AbsenceRequest();
        absenceRequest.setAbsenceDate(dto.getAbsenceDate());
        absenceRequest.setStartTime(dto.getStartTime());
        absenceRequest.setEndTime(dto.getEndTime());
        absenceRequest.setReason(dto.getReason());
        absenceRequest.setRequestDate(LocalDate.now());
        absenceRequest.setStatus(RequestStatus.PENDING);
        absenceRequest.setEmployee(employee);

        AbsenceRequest saved = absenceRequestRepository.save(absenceRequest);
        return toDTO(saved);
    }

    @Override
    public AbsenceRequestDTO approveAbsenceRequest(Long id) {
        AbsenceRequest absenceRequest = findOrThrow(id);
        assertPending(absenceRequest);
        absenceRequest.setStatus(RequestStatus.APPROVED);
        return toDTO(absenceRequestRepository.save(absenceRequest));
    }

    @Override
    public AbsenceRequestDTO rejectAbsenceRequest(Long id) {
        AbsenceRequest absenceRequest = findOrThrow(id);
        assertPending(absenceRequest);
        absenceRequest.setStatus(RequestStatus.REJECTED);
        return toDTO(absenceRequestRepository.save(absenceRequest));
    }

    @Override
    public void deleteAbsenceRequest(Long id) {
        AbsenceRequest absenceRequest = findOrThrow(id);
        if (absenceRequest.getStatus() != RequestStatus.PENDING) {
            throw new InvalidRequestException("Seule une demande en attente peut etre annulee");
        }
        absenceRequestRepository.deleteById(id);
    }

    @Override
    public AbsenceRequestDTO getAbsenceRequestById(Long id) {
        return toDTO(findOrThrow(id));
    }

    @Override
    public List<AbsenceRequestDTO> getAllAbsenceRequests() {
        return absenceRequestRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbsenceRequestDTO> getAbsenceRequestsByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employe introuvable avec l'id : " + employeeId);
        }
        return absenceRequestRepository.findByEmployeeIdOrderByRequestDateDesc(employeeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AbsenceRequest findOrThrow(Long id) {
        return absenceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Demande d'autorisation introuvable avec l'id : " + id));
    }

    private void assertPending(AbsenceRequest absenceRequest) {
        if (absenceRequest.getStatus() != RequestStatus.PENDING) {
            throw new InvalidRequestException(
                    "Cette demande a deja ete traitee (statut actuel : " + absenceRequest.getStatus() + ")");
        }
    }

    // --- Conversion manuelle Entity -> DTO ---

    private AbsenceRequestDTO toDTO(AbsenceRequest absenceRequest) {
        AbsenceRequestDTO dto = new AbsenceRequestDTO();
        dto.setId(absenceRequest.getId());
        dto.setAbsenceDate(absenceRequest.getAbsenceDate());
        dto.setStartTime(absenceRequest.getStartTime());
        dto.setEndTime(absenceRequest.getEndTime());
        dto.setReason(absenceRequest.getReason());
        dto.setRequestDate(absenceRequest.getRequestDate());
        dto.setStatus(absenceRequest.getStatus());
        dto.setEmployeeId(absenceRequest.getEmployee().getId());
        dto.setEmployeeName(
                absenceRequest.getEmployee().getFirstName() + " " + absenceRequest.getEmployee().getLastName());
        return dto;
    }
}