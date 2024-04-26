import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { takeUntil } from 'rxjs';
import { HttpStatusCodeConstants } from 'src/app/common/common-enum';
import { CommonService } from 'src/app/common/common.service';
import { CustomValidator } from 'src/app/common/validator';
import { CommonComponent } from 'src/app/component/common/common-component/common.component';
import { EmployeeAddRequest } from 'src/app/dto/request/system-staff/employee-add-request';
import { ApiResponse } from 'src/app/dto/response/api-response';
import { SpinnerService } from 'src/app/service/spinner.service';
import { EmployeeListService } from 'src/app/service/system/staff/employee/employee-list.service';

@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.scss']
})
export class EmployeeAddComponent extends CommonComponent implements OnInit {
  public createForm: FormGroup = new FormGroup({});
  public showPassword: boolean = false;
  public employeeAddRequest: EmployeeAddRequest = new EmployeeAddRequest();
  constructor(private ref: DynamicDialogRef,
    private commonService: CommonService,
    private employeeListService: EmployeeListService,
    private spinnerService: SpinnerService) {
    super();
  }

  ngOnInit(): void {
    this.initDropdown();
    this.initCreateForm();
  }
  initDropdown() {
    this.initGenderOptions();
    this.initSystemRole();
    this.initEmployeeRole();
  }

  initCreateForm() {
    this.createForm = new FormGroup({
      fullName: new FormControl(),
      email: new FormControl(),
      dateOfBirth: new FormControl(),
      systemRole: new FormControl(),
      username: new FormControl(),
      password: new FormControl(),
      department: new FormControl(),
      role: new FormControl(),
      description: new FormControl(),
      address: new FormControl(),
      phoneNumber: new FormControl(),
      gender: new FormControl(),
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.createForm.controls;
  }

  cancel() {
    this.ref.close();
  }
  create() {
    this.submitted = true;
    this.formRequired();
    if (this.createForm.invalid) {
      return;
    }
    this.employeeAddRequest = this.createForm.value;
    this.employeeAddRequest.dateOfBirth = this.createForm.value.dateOfBirth.getTime();
    // this.employeeListService.addNewEmployee(this.employeeAddRequest).pipe(takeUntil(this.$destroy)).subscribe();
    // this.employeeListService.addNewEmployee(this.employeeAddRequest).pipe(takeUntil(this.$destroy)).subscribe();
    this.employeeListService.addNewEmployee(this.employeeAddRequest).pipe(takeUntil(this.$destroy)).subscribe({
      next: (response: ApiResponse) => {
        if (response.status === HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.ref.close(response.status);
        }
      },
      error: () => this.spinnerService.resetSpinner(),
      complete: () => this.spinnerService.resetSpinner()
    });
  }

  formRequired() {
    let controlRequired = ["fullName", "email", "dateOfBirth", "username", "password", "phoneNumber", "gender"];
    CustomValidator.setValidatorFormGroupMultipleValues(this.createForm, controlRequired);
  }
}
