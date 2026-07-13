import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Department } from '../../../models/department.model';
import { DepartmentService } from '../../../services/department.service';

@Component({
  selector: 'app-department-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './department-form.html',
  styleUrl: './department-form.css'
})
export class DepartmentForm implements OnInit {

  department: Department = { name: '', description: '' };
  isEditMode = false;
  errorMessage = '';

  constructor(
    private departmentService: DepartmentService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.departmentService.getById(Number(idParam)).subscribe({
        next: (data) => (this.department = data),
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Departement introuvable.';
        }
      });
    }
  }

  onSubmit(): void {
    this.errorMessage = '';

    const request = this.isEditMode
      ? this.departmentService.update(this.department.id!, this.department)
      : this.departmentService.create(this.department);

    request.subscribe({
      next: () => this.router.navigate(['/departments']),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Une erreur est survenue.';
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/departments']);
  }
}