package tn.company.hrmanagement.service;

import tn.company.hrmanagement.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);

    void deleteDepartment(Long id);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();
}