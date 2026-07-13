import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { Employee } from '../../../models/employee.model';
import { EmployeeService } from '../../../services/employee.service';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.css'
})
export class EmployeeList implements OnInit {

  employees: Employee[] = [];
  errorMessage = '';

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.getAll().subscribe({
      next: (data) => {
        this.employees = data;
        this.errorMessage = '';
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Erreur lors du chargement des employes.';
      }
    });
  }

  deleteEmployee(id: number | undefined): void {
    if (!id) return;
    if (!confirm('Supprimer cet employe ?')) return;

    this.employeeService.delete(id).subscribe({
      next: () => this.loadEmployees(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors de la suppression.';
      }
    });
  }
}