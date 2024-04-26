import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SystemStaffRoutingModule } from './system-staff-routing.module';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { HrmModule } from 'src/app/hrm.module';
import { TranslateService } from '@ngx-translate/core';
import { EmployeeAddComponent } from './employee-list/employee-add/employee-add.component';


@NgModule({
  declarations: [
    EmployeeListComponent,
    EmployeeAddComponent
  ],
  imports: [
    CommonModule,
    SystemStaffRoutingModule,
    HrmModule
  ],
  exports: [
    EmployeeListComponent
  ],
  providers: [TranslateService]
})
export class SystemStaffModule { }
