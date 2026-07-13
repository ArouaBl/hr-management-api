import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AbsenceRequest } from '../../../models/absence-request.model';
import { Employee } from '../../../models/employee.model';
import { AbsenceRequestService } from '../../../services/absence-request.service';
import { EmployeeService } from '../../../services/employee.service';

@Component({
  selector: 'app-absence-request-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './absence-request-form.html',
  styleUrl: './absence-request-form.css'
})
export class AbsenceRequestForm implements OnInit {

  request: AbsenceRequest = {
    absenceDate: '',
    startTime: '',
    endTime: '',
    reason: '',
    employeeId: 0
  };

  employees: Employee[] = [];
  errorMessage = '';

  constructor(
    private absenceRequestService: AbsenceRequestService,
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.employeeService.getAll().subscribe({
      next: (data) => (this.employees = data),
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Impossible de charger la liste des employes.';
      }
    });
  }

  onSubmit(): void {
    this.errorMessage = '';

    if (this.request.endTime <= this.request.startTime) {
      this.errorMessage = "L'heure de fin doit etre apres l'heure de debut.";
      return;
    }

    this.absenceRequestService.create(this.request).subscribe({
      next: () => this.router.navigate(['/absence-requests']),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Une erreur est survenue.';
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/absence-requests']);
  }
}