import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Dashboard } from '../../models/dashboard.model';
import { DashboardService } from '../../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class DashboardComponent implements OnInit {

  dashboard: Dashboard | null = null;
  errorMessage = '';

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getDashboard().subscribe({
      next: (data) => (this.dashboard = data),
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Erreur lors du chargement des statistiques.';
      }
    });
  }
}