import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { forkJoin, takeUntil } from 'rxjs';
import { ActionStatus, HttpStatusCodeConstants } from 'src/app/common/common-enum';
import { CommonComponent } from 'src/app/component/common/common-component/common.component';
import { ApiResponse } from 'src/app/dto/response/api-response';
import { DropdownResponse } from 'src/app/dto/response/dropdown.response';
import { DropdownService } from 'src/app/service/dropdown.service';
import { TaskLogWorkspaceService } from 'src/app/service/system/task-log/task-log-workspace.service';

@Component({
  selector: 'app-add-workspace',
  templateUrl: './add-workspace.component.html',
  styleUrls: ['./add-workspace.component.scss']
})
export class AddWorkspaceComponent extends CommonComponent implements OnInit {
  public editForm: FormGroup = new FormGroup({});
  public projectDropdown: DropdownResponse[] = [];
  public employeeDropdown: DropdownResponse[] = [];

  constructor(private dropdownService: DropdownService, private tlWorkspaceService: TaskLogWorkspaceService) {
    super();
  }
  
  ngOnInit(): void {
    this.initFormGroup();
    this.initDataAPI();
  }

  private initFormGroup() {
    this.editForm = new FormGroup({
      name: new FormControl(),
      alias: new FormControl(),
      project: new FormControl(),
      curator: new FormControl(),
      member: new FormControl()
    });
  }

  private initDataAPI() {
    forkJoin({
      projectDropdown: this.dropdownService.getDropdownProject(),
      staffDropdown: this.dropdownService.getDropdownStaff()
    }).pipe(takeUntil(this.$destroy)).subscribe({
      next: (forkResponse) => {
        const projectResponse = forkResponse.projectDropdown;
        if (projectResponse && projectResponse.status == HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.projectDropdown = projectResponse.item;
        }

        const staffResponse = forkResponse.staffDropdown;
        if (staffResponse && staffResponse.status == HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.employeeDropdown = staffResponse.item;
        }
      }
    });
  }

  public insertWorkspace(){
    const formValue = this.editForm.value;
    this.tlWorkspaceService.upsertWorkspace(formValue).pipe(takeUntil(this.$destroy)).subscribe({
      next: (response: ApiResponse) => {
        if(response && response.status === HttpStatusCodeConstants.HTTP_STATUS_200){
          this.showToast(ActionStatus.SUCCESS, "Apply workspace success");
        }
      }
    })
  }
}
