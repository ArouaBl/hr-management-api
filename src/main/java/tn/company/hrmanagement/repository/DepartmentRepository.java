package tn.company.hrmanagement.repository;

import tn.company.hrmanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Spring Data JPA génère la requête automatiquement à partir du nom de la méthode.
    // Utilisé dans le service pour vérifier l'unicité du nom avant de sauvegarder.
    boolean existsByName(String name);
}