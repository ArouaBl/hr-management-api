package tn.company.hrmanagement.controller;

import tn.company.hrmanagement.dto.AbsenceRequestDTO;
import tn.company.hrmanagement.service.AbsenceRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/absence-requests")
@RequiredArgsConstructor
@Tag(name = "Absence Requests", description = "Autorisations d'absence : creation, validation, historique")
public class AbsenceRequestController {

    private final AbsenceRequestService absenceRequestService;

    @GetMapping
    @Operation(summary = "Lister toutes les demandes d'autorisation")
    public ResponseEntity<List<AbsenceRequestDTO>> getAllAbsenceRequests() {
        return ResponseEntity.ok(absenceRequestService.getAllAbsenceRequests());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une demande d'autorisation par son id")
    public ResponseEntity<AbsenceRequestDTO> getAbsenceRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(absenceRequestService.getAbsenceRequestById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Historique des autorisations d'absence d'un employe")
    public ResponseEntity<List<AbsenceRequestDTO>> getAbsenceRequestsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(absenceRequestService.getAbsenceRequestsByEmployee(employeeId));
    }

    @PostMapping
    @Operation(summary = "Creer une demande d'autorisation d'absence",
            description = "Statut initial automatique : PENDING. endTime doit etre apres startTime")
    public ResponseEntity<AbsenceRequestDTO> createAbsenceRequest(@Valid @RequestBody AbsenceRequestDTO dto) {
        AbsenceRequestDTO created = absenceRequestService.createAbsenceRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Valider une demande d'autorisation en attente")
    public ResponseEntity<AbsenceRequestDTO> approveAbsenceRequest(@PathVariable Long id) {
        return ResponseEntity.ok(absenceRequestService.approveAbsenceRequest(id));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Refuser une demande d'autorisation en attente")
    public ResponseEntity<AbsenceRequestDTO> rejectAbsenceRequest(@PathVariable Long id) {
        return ResponseEntity.ok(absenceRequestService.rejectAbsenceRequest(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Annuler une demande d'autorisation",
            description = "Uniquement possible si la demande est encore PENDING")
    public ResponseEntity<Void> deleteAbsenceRequest(@PathVariable Long id) {
        absenceRequestService.deleteAbsenceRequest(id);
        return ResponseEntity.noContent().build();
    }
}