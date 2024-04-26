import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HrmSystemRoutingModule } from './hrm-system-routing.module';
import { SystemDashboardComponent } from './system-dashboard/system-dashboard.component';
import { HrmModule } from 'src/app/hrm.module';
import { TranslateService } from '@ngx-translate/core';
import { SystemRoutingComponent } from './system-routing/system-routing.component';

@NgModule({
  declarations: [
    SystemDashboardComponent,
    SystemRoutingComponent
  ],
  imports: [
    CommonModule,
    HrmSystemRoutingModule,
    HrmModule
  ],
  exports: [
    SystemDashboardComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [TranslateService],
})
export class HrmSystemModule { }
