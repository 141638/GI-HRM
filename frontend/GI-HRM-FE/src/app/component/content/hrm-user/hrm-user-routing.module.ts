import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { eRole } from 'src/app/common/common-enum';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: UserDashboardComponent,
    data: {
      expectedRole: eRole.EMPLOYEE
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HrmUserRoutingModule { }
