import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { Department } from '../../../models/department.model';
import { DepartmentService } from '../../../services/department.service';

@Component({
  selector: 'app-department-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './department-list.html',
  styleUrl: './department-list.css'
})
export class DepartmentList implements OnInit {

  departments: Department[] = [];
  errorMessage = '';

  constructor(private departmentService: DepartmentService) {}

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments(): void {
    this.departmentService.getAll().subscribe({
      next: (data) => {
        this.departments = data;
        this.errorMessage = '';
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Erreur lors du chargement des departements.';
      }
    });
  }

  deleteDepartment(id: number | undefined): void {
    if (!id) return;
    if (!confirm('Supprimer ce departement ?')) return;

    this.departmentService.delete(id).subscribe({
      next: () => this.loadDepartments(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors de la suppression.';
      }
    });
  }
}