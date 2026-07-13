package tn.company.hrmanagement.controller;

import tn.company.hrmanagement.dto.DashboardDTO;
import tn.company.hrmanagement.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Statistiques agregees en lecture seule")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Recuperer les statistiques globales",
            description = "Nombre d'employes/departements, repartition par departement, "
                    + "compteurs de conges et autorisations par statut")
    public DashboardDTO getDashboard() {
        return dashboardService.getDashboard();
    }
}