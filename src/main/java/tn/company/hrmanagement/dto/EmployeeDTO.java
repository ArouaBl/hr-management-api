package tn.company.hrmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Le prenom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    private String phone;

    private String address;

    private String position;

    private LocalDate hireDate;

    private Double salary;

    // Ce que le client envoie : juste l'id du departement, pas l'objet complet
    @NotNull(message = "L'id du departement est obligatoire")
    private Long departmentId;

    // Champ en lecture seule : rempli automatiquement dans la reponse,
    // pas requis (et ignore si envoye) dans une requete POST/PUT
    private String departmentName;
}