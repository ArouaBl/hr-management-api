package tn.company.hrmanagement.config;

import tn.company.hrmanagement.model.Role;
import tn.company.hrmanagement.model.User;
import tn.company.hrmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.existsByUsername("admin")) {
            return; // deja cree, ne rien faire
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);
        // Pas d'employe rattache : c'est un compte administrateur "pur"

        userRepository.save(admin);
        System.out.println(">>> Compte admin par defaut cree : username=admin / password=admin123");
    }

}
