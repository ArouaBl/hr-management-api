import { Routes } from '@angular/router';
import { DepartmentList } from './components/departments/department-list/department-list';
import { DepartmentForm } from './components/departments/department-form/department-form';

export const routes: Routes = [
  { path: '', redirectTo: 'departments', pathMatch: 'full' },
  { path: 'departments', component: DepartmentList },
  { path: 'departments/new', component: DepartmentForm },
  { path: 'departments/:id/edit', component: DepartmentForm },
];