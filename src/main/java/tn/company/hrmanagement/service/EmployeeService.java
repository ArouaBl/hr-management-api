package tn.company.hrmanagement.service;

import tn.company.hrmanagement.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

    EmployeeDTO getEmployeeById(Long id);

    List<EmployeeDTO> getAllEmployees();

    List<EmployeeDTO> getEmployeesByDepartment(Long departmentId);
}