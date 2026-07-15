package tn.company.hrmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String username;
    private String role;
    private Long employeeId; // null si le compte n'est pas rattache a un employe
}