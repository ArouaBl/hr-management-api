package tn.company.hrmanagement.repository;

import tn.company.hrmanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    // Utile pour un futur endpoint "employes d'un departement donne"
    List<Employee> findByDepartmentId(Long departmentId);
    // Utilise par le dashboard pour compter les employes par departement
    long countByDepartmentId(Long departmentId);
}