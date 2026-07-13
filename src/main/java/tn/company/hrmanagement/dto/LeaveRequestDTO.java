package tn.company.hrmanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.company.hrmanagement.model.RequestStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDTO {

    private Long id;

    @NotNull(message = "La date de debut est obligatoire")
    private LocalDate startDate;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate endDate;

    private String reason;

    // Champs en lecture seule : remplis automatiquement par le serveur,
    // ignores meme si le client les envoie dans une requete de creation.
    private LocalDate requestDate;
    private RequestStatus status;

    @NotNull(message = "L'id de l'employe est obligatoire")
    private Long employeeId;

    private String employeeName;
}