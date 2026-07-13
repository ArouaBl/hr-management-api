package tn.company.hrmanagement.repository;

import tn.company.hrmanagement.model.AbsenceRequest;
import tn.company.hrmanagement.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AbsenceRequestRepository extends JpaRepository<AbsenceRequest, Long> {

    List<AbsenceRequest> findByEmployeeIdOrderByRequestDateDesc(Long employeeId);

    long countByStatus(RequestStatus status);

    long countByStatusAndRequestDateBetween(RequestStatus status, LocalDate start, LocalDate end);
}