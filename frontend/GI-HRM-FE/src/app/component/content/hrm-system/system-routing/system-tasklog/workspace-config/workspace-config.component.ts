import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageKey } from 'src/app/common/common-enum';

@Component({
  selector: 'app-workspace-config',
  templateUrl: './workspace-config.component.html',
  styleUrls: ['./workspace-config.component.scss']
})
export class WorkspaceConfigComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(private router: Router) {
  }

  public exitWorkspace() {
    window.localStorage.removeItem(LocalStorageKey.WORKSPACE_ID);
    this.router.navigateByUrl('', { skipLocationChange: true })
      .then(() => {
        this.router.navigate(['/system/tasklog/workspace']);
      });
  }
}
