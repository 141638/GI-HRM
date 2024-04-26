import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SystemTasklogRoutingModule } from './system-tasklog-routing.module';
import { HrmModule } from 'src/app/hrm.module';
import { TranslateService } from '@ngx-translate/core';
import { TasklogListComponent } from './tasklog-list/tasklog-list.component';
import { TasklogLogComponent } from './tasklog-log/tasklog-log.component';
import { WorkspaceComponent } from './workspace/workspace.component';
import { WorkspaceConfigComponent } from './workspace-config/workspace-config.component';
import { AddWorkspaceComponent } from './workspace/add-workspace/add-workspace.component';


@NgModule({
  declarations: [
    TasklogListComponent,
    TasklogLogComponent,
    WorkspaceComponent,
    WorkspaceConfigComponent,
    AddWorkspaceComponent
  ],
  imports: [
    CommonModule,
    HrmModule,
    SystemTasklogRoutingModule
  ],
  exports: [
    TasklogListComponent,
    TasklogLogComponent,
    WorkspaceComponent,
    WorkspaceConfigComponent
  ],
  providers: [TranslateService]
})
export class SystemTasklogModule { }
