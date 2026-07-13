import { Routes } from '@angular/router';
import { DepartmentList } from './components/departments/department-list/department-list';
import { DepartmentForm } from './components/departments/department-form/department-form';
import { EmployeeList } from './components/employees/employee-list/employee-list';
import { EmployeeForm } from './components/employees/employee-form/employee-form';
import { LeaveRequestList } from './components/leave-requests/leave-request-list/leave-request-list';
import { LeaveRequestForm } from './components/leave-requests/leave-request-form/leave-request-form';
import { AbsenceRequestList } from './components/absence-requests/absence-request-list/absence-request-list';
import { AbsenceRequestForm } from './components/absence-requests/absence-request-form/absence-request-form';

export const routes: Routes = [
  { path: '', redirectTo: 'departments', pathMatch: 'full' },

  { path: 'departments', component: DepartmentList },
  { path: 'departments/new', component: DepartmentForm },
  { path: 'departments/:id/edit', component: DepartmentForm },

  { path: 'employees', component: EmployeeList },
  { path: 'employees/new', component: EmployeeForm },
  { path: 'employees/:id/edit', component: EmployeeForm },

  { path: 'leave-requests', component: LeaveRequestList },
  { path: 'leave-requests/new', component: LeaveRequestForm },

  { path: 'absence-requests', component: AbsenceRequestList },
  { path: 'absence-requests/new', component: AbsenceRequestForm },
];