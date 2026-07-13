package tn.company.hrmanagement.repository;

import tn.company.hrmanagement.model.LeaveRequest;
import tn.company.hrmanagement.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Historique d'un employe, du plus recent au plus ancien
    List<LeaveRequest> findByEmployeeIdOrderByRequestDateDesc(Long employeeId);

    // Utilise par le dashboard : nombre de demandes par statut (PENDING/APPROVED/REJECTED)
    long countByStatus(RequestStatus status);

    // Utilise par le dashboard : demandes traitees dans une periode donnee (ex : ce mois-ci)
    long countByStatusAndRequestDateBetween(RequestStatus status, LocalDate start, LocalDate end);
}