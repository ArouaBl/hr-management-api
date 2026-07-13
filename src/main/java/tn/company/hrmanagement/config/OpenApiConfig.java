package tn.company.hrmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI hrManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HR Management API")
                        .description("API REST pour la gestion des employes, departements, "
                                + "demandes de conges et autorisations d'absence.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ton Nom")
                                .email("ton.email@example.com")));
    }
}