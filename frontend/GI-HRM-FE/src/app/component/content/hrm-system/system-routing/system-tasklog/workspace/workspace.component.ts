import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { firstValueFrom, takeUntil } from 'rxjs';
import { ActionStatus, HttpStatusCodeConstants, LocalStorageKey } from 'src/app/common/common-enum';
import { CommonComponent } from 'src/app/component/common/common-component/common.component';
import { ApiResponse } from 'src/app/dto/response/api-response';
import { DropdownResponse } from 'src/app/dto/response/dropdown.response';
import { TaskLogWorkspaceResponse } from 'src/app/dto/response/system-tasklog/tasklog-workspace.response';
import { DropdownService } from 'src/app/service/dropdown.service';
import { SpinnerService } from 'src/app/service/spinner.service';
import { TaskLogWorkspaceService } from 'src/app/service/system/task-log/task-log-workspace.service';
import { AddWorkspaceComponent } from './add-workspace/add-workspace.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.scss']
})
export class WorkspaceComponent extends CommonComponent implements OnInit {
  public projectDropdown: DropdownResponse[] = [];
  public staffDropdown: DropdownResponse[] = [];
  public chosenWorkspaceId: number | undefined;

  public searchForm: FormGroup = new FormGroup({});
  public workspaceList: TaskLogWorkspaceResponse[] = [];

  constructor(
    private tlWorkspaceService: TaskLogWorkspaceService,
    private spinnerService: SpinnerService,
    private changeRef: ChangeDetectorRef,
    private dropdownService: DropdownService,
    private router: Router,
    private dialogService: DialogService,
    private dialogRef: DynamicDialogRef,
    private translateService: TranslateService) {
    super();
  }

  ngOnInit(): void {
    this.initChosenWorkspace();
    this.initFormGroup();
    this.initTableColumn();
    this.initDataAPI();
    this.search();
    // this.tlWorkspaceService.dropdownTestSSE();
  }

  ngAfterViewChecked(): void {
    this.changeRef.detectChanges();
  }

  initChosenWorkspace() {
    this.chosenWorkspaceId = Number(window.localStorage.getItem(LocalStorageKey.WORKSPACE_ID)!);
    if (this.chosenWorkspaceId) {
      this.applyWorkspace(this.chosenWorkspaceId);
    }
  }

  private initFormGroup() {
    this.searchForm = new FormGroup({
      name: new FormControl(),
      projectId: new FormControl(),
      staffId: new FormControl()
    });
  }

  private initTableColumn() {
    this.colTemplate = [
      { header: 'systemTasklog.workspace.name', colWidth: '20%' },
      { header: 'systemTasklog.workspace.alias', colWidth: '15%' },
      { header: 'systemTasklog.workspace.projectName', colWidth: '20%' },
      { header: 'systemTasklog.workspace.projectCuratorName', colWidth: '20%' },
      { header: 'systemTasklog.workspace.totalMember', colWidth: '15%' },
      { header: '', colWidth: '15%' },
    ];
  }
  private initDataAPI() {
    this.dropdownService.getDropdownProject().pipe(takeUntil(this.$destroy)).subscribe({
      next: (response: ApiResponse) => {
        if (response && response.status == HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.projectDropdown = response.item;
        }
      }
    });

    this.dropdownService.getDropdownStaff().pipe(takeUntil(this.$destroy)).subscribe({
      next: (response: ApiResponse) => {
        if (response && response.status == HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.staffDropdown = response.item;
        }
      }
    });
  }

  public async addWorkspace() {
    let header: string = await firstValueFrom(this.translateService.get('header.addWorkspace'));
    this.dialogRef = this.dialogService.open(AddWorkspaceComponent, {
      header: header,
      width: '30%'
    });

    this.dialogRef.onClose.pipe(takeUntil(this.$destroy)).subscribe({
      next: (response) => {

      }
    });
  }

  public search(searchAfter?: any) {
    this.workspaceList = [];
    this.tlWorkspaceService.search(this.searchForm.value).pipe(takeUntil(this.$destroy)).subscribe({
      next: (apiResponse: ApiResponse) => {
        if (apiResponse && apiResponse.status === HttpStatusCodeConstants.HTTP_STATUS_200) {
          this.workspaceList.push(apiResponse.item);
        }
      },
      error: () => this.showToast(ActionStatus.FAILED, null),
      complete: () => this.spinnerService.resetSpinner()
    });
  }

  public pageClick($event: any) {

  }

  public applyWorkspace(workspaceId: number) {
    window.localStorage.setItem(LocalStorageKey.WORKSPACE_ID, String(workspaceId));
    this.chosenWorkspaceId = workspaceId;
    this.router.navigateByUrl('', { skipLocationChange: true })
      .then(() => {
        this.router.navigate(['/system/tasklog/logs']);
      });
  }
}