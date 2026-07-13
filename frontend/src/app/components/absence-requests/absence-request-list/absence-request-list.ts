import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { AbsenceRequest } from '../../../models/absence-request.model';
import { AbsenceRequestService } from '../../../services/absence-request.service';

@Component({
  selector: 'app-absence-request-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './absence-request-list.html',
  styleUrl: './absence-request-list.css'
})
export class AbsenceRequestList implements OnInit {

  requests: AbsenceRequest[] = [];
  errorMessage = '';

  constructor(private absenceRequestService: AbsenceRequestService) {}

  ngOnInit(): void {
    this.loadRequests();
  }

  loadRequests(): void {
    this.absenceRequestService.getAll().subscribe({
      next: (data) => {
        this.requests = data;
        this.errorMessage = '';
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = "Erreur lors du chargement des demandes d'autorisation.";
      }
    });
  }

  approve(id: number | undefined): void {
    if (!id) return;
    this.absenceRequestService.approve(id).subscribe({
      next: () => this.loadRequests(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors de la validation.';
      }
    });
  }

  reject(id: number | undefined): void {
    if (!id) return;
    this.absenceRequestService.reject(id).subscribe({
      next: () => this.loadRequests(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors du refus.';
      }
    });
  }

  deleteRequest(id: number | undefined): void {
    if (!id) return;
    if (!confirm("Annuler cette demande d'autorisation ?")) return;

    this.absenceRequestService.delete(id).subscribe({
      next: () => this.loadRequests(),
      error: (err) => {
        console.error(err);
        this.errorMessage = err.error?.message || 'Erreur lors de la suppression.';
      }
    });
  }
}