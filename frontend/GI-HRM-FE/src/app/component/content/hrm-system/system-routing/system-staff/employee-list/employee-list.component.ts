import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ActionStatus, ConfirmActionType, HttpStatusCodeConstants, SearchAfter } from 'src/app/common/common-enum';
import { CommonComponent } from 'src/app/component/common/common-component/common.component';
import { EmployeeListSearchRequest } from 'src/app/dto/request/system-staff/employee-list-search-request';
import { ApiResponse } from 'src/app/dto/response/api-response';
import { EmployeeListResponse } from 'src/app/dto/response/system-staff/EmployeeListResponse';
import { SpinnerService } from 'src/app/service/spinner.service';
import { EmployeeListService } from 'src/app/service/system/staff/employee/employee-list.service';
import { EmployeeAddComponent } from './employee-add/employee-add.component';
import { MessageService } from 'primeng/api';
import { takeUntil } from 'rxjs';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent extends CommonComponent implements OnInit {
  public searchForm: FormGroup = new FormGroup({});
  public inworkingEmployeeList: EmployeeListResponse[] = [];
  public employeeListSearchRequest: EmployeeListSearchRequest = new EmployeeListSearchRequest();
  public advanceSearchCollapsed: boolean = true;
  public rotateClass: string = "button-rotate-left";
  public actionId: number | undefined;
  constructor(
    private ref: DynamicDialogRef,
    private dialogService: DialogService,
    private employeeListService: EmployeeListService,
    private spinnerService: SpinnerService,
    private messageService: MessageService) {
    super();
  }

  ngOnInit(): void {
    this.initStaticDropdowns();
    this.initColTemplate();
    this.initSearchForm();
    this.initTableContent();
  }
  initStaticDropdowns() {
    this.initSystemRole();
    this.initEmployeeRole();
  }
  initColTemplate() {
    this.colTemplate = [
      { 'colWidth': '45%', 'header': 'systemStaff.employeeName', },
      { 'colWidth': '15%', 'header': 'systemStaff.employeeCode' },
      { 'colWidth': '15%', 'header': 'systemStaff.employeeDepartment' },
      { 'colWidth': '15%', 'header': 'systemStaff.dateOfBirth' },
      { 'colWidth': '10%', 'header': '' }
    ];
  }
  get actionMenuItems() {
    let items = [
      {
        label: 'Options',
        items: [
          {
            label: 'Update',
            icon: 'pi pi-pencil',
            command: () => {
              this.onUpdate();
            }
          },
          {
            label: 'Delete',
            icon: 'pi pi-times',
            command: () => {
              this.onDelete();
            }
          }
        ]
      }
    ];
    return items;
  }

  initSearchForm() {
    this.searchForm = new FormGroup({
      name: new FormControl(),
      email: new FormControl(),
      insystemRole: new FormControl(),
      department: new FormControl(),
      employeeCode: new FormControl(),
      dateOfBirth: new FormControl(),
      generalKeys: new FormControl()
    });
  }

  get form() {
    return this.searchForm.controls;
  }

  onSearch(searchAfter?: number) {
    this.employeeListSearchRequest = this.searchForm.value;
    this.employeeListSearchRequest.pageSize = this.pageSize;
    this.employeeListSearchRequest.currentPage = this.currentPage;
    this.skeletonLoading = true;
    this.employeeListService.search(this.employeeListSearchRequest).subscribe({
      next: (response: ApiResponse) => {
        if (response.status === HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.inworkingEmployeeList = response.item.items;
          this.totalRecord = response.item.totalItems;
        }
      },
      error: () => { this.spinnerService.resetSpinner(); this.skeletonLoading = false },
      complete: () => {
        this.skeletonLoading = false;
        if (searchAfter !== undefined) {
          this.showToast(ActionStatus.SUCCESS, null, searchAfter);
        }
        this.spinnerService.resetSpinner();
      }
    });
  }

  onCreateError() {
    this.messageService.add({ severity: 'error', detail: 'Action failed' });
  }

  onUpdate() {

  }

  onDelete() {
    this.confirmTrigger(ConfirmActionType.DELETE);
  }

  deleteConfirm() {
    this.employeeListService.delete(this.actionId!).pipe(takeUntil(this.$destroy)).subscribe({
      next: (apiResponse: ApiResponse) => {
        if (apiResponse.status === HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.onSearch(SearchAfter.DELETE);
        } else {
          this.actionId = undefined;
          this.showToast(ActionStatus.FAILED, null, SearchAfter.DELETE);
        }
      },
      error: () => {
        this.actionId = undefined;
        this.showToast(ActionStatus.FAILED, null, SearchAfter.DELETE);
      }
    });
  }

  initTableContent() {
    this.onSearch();
  }

  triggerAddPopup() {
    this.ref = this.dialogService.open(EmployeeAddComponent, {
      header: 'Add Employee',
      width: '40vw'
    });
    this.ref.onClose.subscribe(item => {
      if (item && item === HttpStatusCodeConstants.HTTP_STATUS_200) {
        this.onSearch(SearchAfter.CREATE);
      }
    });
  }

  get maxId() {
    return this.inworkingEmployeeList[this.inworkingEmployeeList.length - 1];
  }
  pageClick(event: any) {
    this.currentPage = event.page + 1;
    this.onSearch();
  }

  advanceSearchToggle() {
    this.advanceSearchCollapsed = !this.advanceSearchCollapsed;
    if (this.advanceSearchCollapsed) {
      this.rotateClass = "icon-rotate-right";
    } else {
      this.rotateClass = "icon-rotate-left";
    }
    let test = document.querySelector('.p-panel .p-panel-icons-end button') as HTMLElement;
    test.click();
  }
}

