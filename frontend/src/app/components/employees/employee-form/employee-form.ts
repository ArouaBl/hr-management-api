import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Employee } from '../../../models/employee.model';
import { Department } from '../../../models/department.model';
import { EmployeeService } from '../../../services/employee.service';
import { DepartmentService } from '../../../services/department.service';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-form.html',
  styleUrl: './employee-form.css'
})
export class EmployeeForm implements OnInit {

  employee: Employee = {
    firstName: '',
    lastName: '',
    email: '',
    departmentId: 0
  };

  departments: Department[] = [];
  isEditMode = false;
  errorMessage = '';

  constructor(
    private employeeService: EmployeeService,
    private departmentService: DepartmentService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Toujours charger la liste des departements pour remplir le dropdown
    this.departmentService.getAll().subscribe({
      next: (data) => (this.departments = data),
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Impossible de charger la liste des departements.';
      }
    });

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.employeeService.getById(Number(idParam)).subscribe({
        next: (data) => (this.employee = data),
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Employe introuvable.';
        }
      });
    }
  }

  onSubmit(): void {
    this.errorMessage = '';

    const request = this.isEditMode
      ? this.employeeService.update(this.employee.id!, this.employee)
      : this.employeeService.create(this.employee);

    request.subscribe({
      next: () => this.router.navigate(['/employees']),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Une erreur est survenue.';
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/employees']);
  }
}