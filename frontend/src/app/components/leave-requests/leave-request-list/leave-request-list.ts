import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { LeaveRequest } from '../../../models/leave-request.model';
import { LeaveRequestService } from '../../../services/leave-request.service';

@Component({
  selector: 'app-leave-request-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './leave-request-list.html',
  styleUrl: './leave-request-list.css'
})
export class LeaveRequestList implements OnInit {

  requests: LeaveRequest[] = [];
  errorMessage = '';

  constructor(private leaveRequestService: LeaveRequestService) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.leaveRequestService.getAll().subscribe({
      next: (data) => {
        this.requests = data;
        this.errorMessage = '';
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Erreur lors du chargement des demandes de conge.';
      }
    });
  }

  approve(id: number | undefined): void {
    if (!id) return;
    this.leaveRequestService.approve(id).subscribe({
      next: () => this.loadRequests(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors de la validation.';
      }
    });
  }

  reject(id: number | undefined): void {
    if (!id) return;
    this.leaveRequestService.reject(id).subscribe({
      next: () => this.loadRequests(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors du refus.';
      }
    });
  }

  deleteRequest(id: number | undefined): void {
    if (!id) return;
    if (!confirm('Annuler cette demande de conge ?')) return;

    this.leaveRequestService.delete(id).subscribe({
      next: () => this.loadRequests(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors de la suppression.';
      }
    });
  }
}