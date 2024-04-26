import { Component, OnInit } from '@angular/core';
import { CommonComponent } from 'src/app/component/common/common-component/common.component';
import { TaskLogService } from 'src/app/service/system/task-log/task-log.service';

@Component({
  selector: 'app-tasklog-list',
  templateUrl: './tasklog-list.component.html',
  styleUrls: ['./tasklog-list.component.scss']
})
export class TasklogListComponent extends CommonComponent implements OnInit {
  constructor(private taskLogService: TaskLogService) {
    super();
  }
  ngOnInit(): void {
      this.taskLogService.upsertCategory({ name: `API-TEST-${1}`, color: "red" }).subscribe();
    
  }
}
