import { Component, OnDestroy } from '@angular/core';
import { Paginator } from 'primeng/paginator';
import { ReplaySubject } from 'rxjs';
import { ActionStatus } from 'src/app/common/common-enum';
import { SearchAfter } from 'src/app/common/common-enum';
import { PageSize } from 'src/app/common/common-enum';
import { CustomValidator } from 'src/app/common/validator';
import { DropDownRequest } from 'src/app/dto/request/dropdown-request';
import { ColTemplate, ConfirmAction, ToastBody } from '../common-dto';

@Component({
  selector: 'app-common-component',
  templateUrl: './common.component.html',
  styleUrls: ['./common.component.scss']
})
export class CommonComponent implements OnDestroy {
  public currentPage: number = 1;
  public totalRecord: number = 0;
  public pageSize: number = PageSize.PAGE_SIZE;
  public pageLinkSize: number = PageSize.PAGE_LINK_SIZE;
  public clickLock: boolean = false;
  public submitted: boolean = false;

  public colTemplate: ColTemplate[] = [];
  public insystemRole: DropDownRequest[] = [];
  public employeeRole: DropDownRequest[] = [];
  public genderOptions: DropDownRequest[] = [];
  public toastRequest: ToastBody | undefined;
  public confirmAction: ConfirmAction | undefined;

  public skeletonLoading: boolean = false;
  constructor() { }

  public $destroy: ReplaySubject<boolean> = new ReplaySubject(1);
  ngOnDestroy() {
    this.$destroy.next(true);
    this.$destroy.complete();
  }

  returnCurrentPage() {
    this.currentPage = 1;
    return this.currentPage;
  }
  returnPage(paginator: Paginator) {
    return paginator.changePage(0);
  }
  departments = [
    { value: 0, name: 'Java' },
    { value: 1, name: 'React Native' },
    { value: 2, name: 'Flutter' },
    { value: 3, name: 'Ruby' },
    { value: 4, name: 'Rust' },
  ]
  getDepartment(value: number) {
    if (!CustomValidator.checkIsNullEmptyUndefined(value)) {
      return this.departments.filter(item => item.value === value)[0].name;
    }
    return undefined;
  }
  initSystemRole() {
    this.insystemRole = [
      { value: 0, name: 'systemStaff.isSystemRoles.bod', bgColor: '#FF451A' },
      { value: 1, name: 'systemStaff.isSystemRoles.hr', bgColor: '#FFD54F' },
      { value: 2, name: 'systemStaff.isSystemRoles.dev', bgColor: '#ECCFFF' },
      { value: 3, name: 'systemStaff.isSystemRoles.leader', bgColor: '#FFD8B2' },
      { value: 4, name: 'systemStaff.isSystemRoles.guest', bgColor: '#515151' }
    ]
  }
  getInsystemRole(value: number) {
    return this.insystemRole.filter(item => item.value === value)[0];
  }
  initGenderOptions() {
    this.genderOptions = [
      { value: 0, name: 'systemStaff.gender.female' },
      { value: 1, name: 'systemStaff.gender.male' },
      { value: 2, name: 'systemStaff.gender.other' }
    ]
  }
  initEmployeeRole() {
    this.employeeRole = [
      { value: 0, name: 'systemStaff.employeeRoles.founder' },
      { value: 1, name: 'systemStaff.employeeRoles.ceo' },
      { value: 2, name: 'systemStaff.employeeRoles.hrm' },
      { value: 3, name: 'systemStaff.employeeRoles.hr' },
      { value: 4, name: 'systemStaff.employeeRoles.leader' },
      { value: 5, name: 'systemStaff.employeeRoles.dev' },
      { value: 6, name: 'systemStaff.employeeRoles.sale' },
      { value: 7, name: 'systemStaff.employeeRoles.marketing' }
    ]
  }
  getEmployeeRole(value: number) {
    return this.employeeRole.filter(item => item.value === value)[0];
  }

  showToast(action: number, message?: string | null, searchAfter?: number) {
    let toastMessage;
    if (searchAfter) {
      switch (searchAfter) {
        case SearchAfter.CREATE:
          toastMessage = action === ActionStatus.SUCCESS ? 'toastMessage.success.create' : 'toastMessage.failed.create'
          break;
        case SearchAfter.UPDATE:
          toastMessage = action === ActionStatus.SUCCESS ? 'toastMessage.success.update' : 'toastMessage.failed.update'
          break;
        case SearchAfter.DELETE:
          toastMessage = action === ActionStatus.SUCCESS ? 'toastMessage.success.delete' : 'toastMessage.failed.delete'
          break;
        default:
          break;
      }
      if (message && message !== null) {
        toastMessage = message;
      }
    }
    this.toastRequest = {
      action: action,
      message: toastMessage,
      header: action === ActionStatus.SUCCESS ? 'toastMessage.success.summary' : 'toastMessage.failed.summary'
    }
  }

  confirmTrigger(action: number, header?: string, message?: string, icon?: string) {
    this.confirmAction = { action: action, header: header, message: message, icon: icon };
  }
}
