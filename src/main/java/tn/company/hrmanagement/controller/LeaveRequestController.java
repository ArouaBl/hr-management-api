package tn.company.hrmanagement.controller;

import tn.company.hrmanagement.dto.LeaveRequestDTO;
import tn.company.hrmanagement.service.LeaveRequestService;
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
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
@Tag(name = "Leave Requests", description = "Demandes de conge : creation, validation, refus, historique")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @GetMapping
    @Operation(summary = "Lister toutes les demandes de conge")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une demande de conge par son id")
    public ResponseEntity<LeaveRequestDTO> getLeaveRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Historique des demandes de conge d'un employe")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveRequestService.getLeaveRequestsByEmployee(employeeId));
    }

    @PostMapping
    @Operation(summary = "Creer une demande de conge",
            description = "Statut initial automatique : PENDING. endDate doit etre >= startDate")
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@Valid @RequestBody LeaveRequestDTO dto) {
        LeaveRequestDTO created = leaveRequestService.createLeaveRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Valider une demande de conge en attente")
    public ResponseEntity<LeaveRequestDTO> approveLeaveRequest(@PathVariable Long id) {
        return ResponseEntity.ok(leaveRequestService.approveLeaveRequest(id));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Refuser une demande de conge en attente")
    public ResponseEntity<LeaveRequestDTO> rejectLeaveRequest(@PathVariable Long id) {
        return ResponseEntity.ok(leaveRequestService.rejectLeaveRequest(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Annuler une demande de conge",
            description = "Uniquement possible si la demande est encore PENDING")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.noContent().build();
    }
}