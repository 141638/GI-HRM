import { Component, ChangeDetectorRef, OnInit } from '@angular/core';
import { IsActiveMatchOptions, Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { LocalStorageKey } from 'src/app/common/common-enum';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public menuItems: MenuItem[] = [];
  constructor(private changeDetector: ChangeDetectorRef, private router: Router) {
  }

  ngOnInit(): void {
    this.createMenuItems();
    this.toExpandWorkspace();
  }

  get haveChosenWorkspace(): boolean {
    let workspaceId = window.localStorage.getItem(LocalStorageKey.WORKSPACE_ID);
    return workspaceId !== null && workspaceId !== undefined;
  }

  ngAfterViewInit() {
    this.changeDetector.detectChanges();
  }

  private toExpandWorkspace() {
    if (this.isActivatingRoute('/system/tasklog/logs') && this.haveChosenWorkspace) {
      this.menuItems.find(item => item.label === 'Tasklog')!.expanded = true
    }
  }

  private createMenuItems() {
    this.menuItems = [
      {
        label: 'Dashboard',
        icon: 'bx bxs-dashboard',
        routerLink: ['/system/dashboard'],
        routerLinkActiveOptions: { exact: true },
        visible: true
      },
      {
        label: 'Employee',
        icon: 'bx bxs-user',
        routerLink: '/system/staff/',
        expanded: this.router.url.indexOf('/system/staff/') !== -1,
        items: [
          { label: 'Employee List', routerLink: ['/system/staff/employee'], routerLinkActiveOptions: { exact: true }, visible: true },
          { label: 'Profile', routerLink: ['/system/staff/profile'], routerLinkActiveOptions: { exact: true }, visible: true },
          { label: 'Leave Request', routerLink: ['/system/staff/leave-request'], routerLinkActiveOptions: { exact: true }, visible: true },
          { label: 'Meeting Request', routerLink: ['/system/staff/meeting-request'], routerLinkActiveOptions: { exact: true }, visible: true },
          { label: 'Department', routerLink: ['/system/staff/department'], routerLinkActiveOptions: { exact: true }, visible: true },
        ]
      },
      {
        label: 'Tasklog',
        icon: 'bx bx-task',
        routerLink: '/system/tasklog/',
        replaceUrl: false,
        expanded: this.router.url.indexOf('/system/tasklog/') !== -1,
        items: [
          { label: 'Workspace', routerLink: ['/system/tasklog/workspace'], routerLinkActiveOptions: { exact: true }, visible: !this.haveChosenWorkspace },
          { label: 'New Issue', routerLink: ['/system/tasklog/logs'], routerLinkActiveOptions: { exact: true }, visible: this.haveChosenWorkspace },
          { label: 'Issues', routerLink: ['/system/tasklog/list'], routerLinkActiveOptions: { exact: true }, visible: this.haveChosenWorkspace },
          { label: 'Workspace Config', routerLink: ['/system/tasklog/config'], routerLinkActiveOptions: { exact: true }, visible: this.haveChosenWorkspace }
        ]
      }
    ];
    this.turnOffParentRouterLink();
  }

  private turnOffParentRouterLink() {
    [...(document.getElementsByClassName('p-toggleable-content') as any)].forEach(item => {
      item.stopPropagation();
    });
  }

  private isActivatingRoute(url: string): boolean {
    let matchOptions: IsActiveMatchOptions = {
      paths: 'exact',
      fragment: 'exact',
      matrixParams: 'exact',
      queryParams: 'exact',
    };
    return this.router.isActive(url, matchOptions);
  }
}
