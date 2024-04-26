import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { eRole } from 'src/app/common/common-enum';
import { EmployeeListComponent } from './employee-list/employee-list.component';

const routes: Routes = [
  {
    path: 'employee',
    component: EmployeeListComponent,
    data: {
      expectedRole: eRole.HR_STAFF
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemStaffRoutingModule { }
