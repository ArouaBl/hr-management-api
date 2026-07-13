package tn.company.hrmanagement.service.impl;

import tn.company.hrmanagement.dto.EmployeeDTO;
import tn.company.hrmanagement.exception.DuplicateResourceException;
import tn.company.hrmanagement.exception.ResourceNotFoundException;
import tn.company.hrmanagement.model.Department;
import tn.company.hrmanagement.model.Employee;
import tn.company.hrmanagement.repository.DepartmentRepository;
import tn.company.hrmanagement.repository.EmployeeRepository;
import tn.company.hrmanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Un employe avec cet email existe deja : " + dto.getEmail());
        }

        Department department = findDepartmentOrThrow(dto.getDepartmentId());

        Employee employee = toEntity(dto, department);
        Employee saved = employeeRepository.save(employee);
        return toDTO(saved);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe introuvable avec l'id : " + id));

        boolean emailChanged = !existing.getEmail().equals(dto.getEmail());
        if (emailChanged && employeeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Un employe avec cet email existe deja : " + dto.getEmail());
        }

        Department department = findDepartmentOrThrow(dto.getDepartmentId());

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());
        existing.setPosition(dto.getPosition());
        existing.setHireDate(dto.getHireDate());
        existing.setSalary(dto.getSalary());
        existing.setDepartment(department);

        Employee updated = employeeRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe introuvable avec l'id : " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe introuvable avec l'id : " + id));
        return toDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(Long departmentId) {
        findDepartmentOrThrow(departmentId); // 404 clair si le departement n'existe pas
        return employeeRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private Department findDepartmentOrThrow(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Departement introuvable avec l'id : " + departmentId));
    }

    // --- Conversions manuelles Entity <-> DTO ---

    private EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setAddress(employee.getAddress());
        dto.setPosition(employee.getPosition());
        dto.setHireDate(employee.getHireDate());
        dto.setSalary(employee.getSalary());
        dto.setDepartmentId(employee.getDepartment().getId());
        dto.setDepartmentName(employee.getDepartment().getName());
        return dto;
    }

    private Employee toEntity(EmployeeDTO dto, Department department) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setPosition(dto.getPosition());
        employee.setHireDate(dto.getHireDate());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);
        return employee;
    }
}