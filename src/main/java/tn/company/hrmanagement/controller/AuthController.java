package tn.company.hrmanagement.controller;

import tn.company.hrmanagement.dto.LoginRequest;
import tn.company.hrmanagement.dto.LoginResponse;
import tn.company.hrmanagement.security.JwtUtil;
import tn.company.hrmanagement.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentification (login)")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(summary = "Se connecter", description = "Retourne un token JWT a utiliser dans le header Authorization")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Nom d'utilisateur ou mot de passe incorrect");
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String role = principal.getUser().getRole().name();
        String token = jwtUtil.generateToken(principal.getUsername(), role);

        Long employeeId = principal.getUser().getEmployee() != null
                ? principal.getUser().getEmployee().getId()
                : null;

        return new LoginResponse(token, principal.getUsername(), role, employeeId);
    }
}