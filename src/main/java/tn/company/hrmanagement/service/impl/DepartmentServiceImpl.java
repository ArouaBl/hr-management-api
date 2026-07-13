package tn.company.hrmanagement.service.impl;

import tn.company.hrmanagement.dto.DepartmentDTO;
import tn.company.hrmanagement.exception.DuplicateResourceException;
import tn.company.hrmanagement.exception.ResourceNotFoundException;
import tn.company.hrmanagement.model.Department;
import tn.company.hrmanagement.repository.DepartmentRepository;
import tn.company.hrmanagement.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        // Règle métier : pas deux départements avec le même nom
        if (departmentRepository.existsByName(departmentDTO.getName())) {
            throw new DuplicateResourceException(
                    "Un departement avec ce nom existe deja : " + departmentDTO.getName());
        }

        Department department = toEntity(departmentDTO);
        Department saved = departmentRepository.save(department);
        return toDTO(saved);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Departement introuvable avec l'id : " + id));

        boolean nameChanged = !existing.getName().equals(departmentDTO.getName());
        if (nameChanged && departmentRepository.existsByName(departmentDTO.getName())) {
            throw new DuplicateResourceException(
                    "Un departement avec ce nom existe deja : " + departmentDTO.getName());
        }

        existing.setName(departmentDTO.getName());
        existing.setDescription(departmentDTO.getDescription());

        Department updated = departmentRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Departement introuvable avec l'id : " + id);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Departement introuvable avec l'id : " + id));
        return toDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Conversions manuelles Entity <-> DTO ---
    // On ne renvoie jamais l'entité JPA directement au client : on passe toujours par le DTO.

    private DepartmentDTO toDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getDescription());
    }

    private Department toEntity(DepartmentDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        return department;
    }
}