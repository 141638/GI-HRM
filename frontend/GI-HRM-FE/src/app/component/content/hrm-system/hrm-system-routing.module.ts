import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { eRole, NavSystemRouter } from 'src/app/common/common-enum';
import { SystemDashboardComponent } from './system-dashboard/system-dashboard.component';
import { SystemRoutingComponent } from './system-routing/system-routing.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: SystemDashboardComponent,
    data: {
      expectedRole: eRole.HR
    },
  },
  {
    path: '',
    component: SystemRoutingComponent,
    children: [
      {
        path: NavSystemRouter.STAFF,
        loadChildren: () => import('./system-routing/system-staff/system-staff.module').then((m) => m.SystemStaffModule)
      }
    ]
  },
  {
    path: '',
    component: SystemRoutingComponent,
    children: [
      {
        path: NavSystemRouter.TASKLOG,
        loadChildren: () => import('./system-routing/system-tasklog/system-tasklog.module').then((m) => m.SystemTasklogModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HrmSystemRoutingModule { }
