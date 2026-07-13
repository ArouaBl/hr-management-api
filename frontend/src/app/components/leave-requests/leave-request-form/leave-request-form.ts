import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { LeaveRequest } from '../../../models/leave-request.model';
import { Employee } from '../../../models/employee.model';
import { LeaveRequestService } from '../../../services/leave-request.service';
import { EmployeeService } from '../../../services/employee.service';

@Component({
  selector: 'app-leave-request-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './leave-request-form.html',
  styleUrl: './leave-request-form.css'
})
export class LeaveRequestForm implements OnInit {

  request: LeaveRequest = {
    startDate: '',
    endDate: '',
    reason: '',
    employeeId: 0
  };

  employees: Employee[] = [];
  errorMessage = '';

  constructor(
    private leaveRequestService: LeaveRequestService,
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

    this.leaveRequestService.create(this.request).subscribe({
      next: () => this.router.navigate(['/leave-requests']),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Une erreur est survenue.';
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/leave-requests']);
  }
}