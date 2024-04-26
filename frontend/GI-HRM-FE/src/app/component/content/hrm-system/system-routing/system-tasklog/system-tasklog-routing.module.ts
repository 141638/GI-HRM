import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { eRole } from 'src/app/common/common-enum';
import { TasklogListComponent } from './tasklog-list/tasklog-list.component';
import { TasklogLogComponent } from './tasklog-log/tasklog-log.component';
import { WorkspaceComponent } from './workspace/workspace.component';
import { WorkspaceConfigComponent } from './workspace-config/workspace-config.component';

const routes: Routes = [
  {
    path: 'workspace',
    component: WorkspaceComponent,
    data: []
  },
  {
    path: 'list',
    component: TasklogListComponent,
    data: {
      expectedRole: [eRole.LEADER, eRole.HR]
    }
  },
  {
    path: 'logs/{log-code}',
    component: TasklogLogComponent,
    data: {
      expectedRole: [eRole.LEADER, eRole.HR]
    }
  },
  {
    path: 'logs',
    component: TasklogLogComponent
  },
  {
    path: 'config',
    component: WorkspaceConfigComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemTasklogRoutingModule { }
