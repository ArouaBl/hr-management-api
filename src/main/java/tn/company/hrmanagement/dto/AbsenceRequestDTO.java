package tn.company.hrmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.company.hrmanagement.model.RequestStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbsenceRequestDTO {

    private Long id;

    @NotNull(message = "La date d'absence est obligatoire")
    private LocalDate absenceDate;

    @NotNull(message = "L'heure de debut est obligatoire")
    private LocalTime startTime;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime endTime;

    private String reason;

    // Champs en lecture seule : remplis automatiquement par le serveur
    private LocalDate requestDate;
    private RequestStatus status;

    @NotNull(message = "L'id de l'employe est obligatoire")
    private Long employeeId;

    private String employeeName;
}