import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HrmUserRoutingModule } from './hrm-user-routing.module';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { HrmModule } from 'src/app/hrm.module';
import { TranslateService } from '@ngx-translate/core';


@NgModule({
  declarations: [
    UserDashboardComponent
  ],
  imports: [
    CommonModule,
    HrmUserRoutingModule,
    HrmModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [TranslateService],
})
export class HrmUserModule { }
