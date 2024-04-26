import { Component, OnChanges, SimpleChanges, Input } from '@angular/core';
import { CommonComponent } from '../common-component/common.component';
import { MessageService } from 'primeng/api';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { ToastBody } from '../common-dto';

@Component({
  selector: 'app-common-toast',
  templateUrl: './common-toast.component.html',
  styleUrls: ['./common-toast.component.scss']
})
export class CommonToastComponent implements OnChanges {
  @Input() toastAction: ToastBody | undefined;

  constructor(
    private messageService: MessageService,
    public tokenStore: TokenStorageService) {
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (this.toastAction?.action !== undefined) {
      this.toastDisplay(this.toastAction);
    }
  }

  toastDisplay(toastAction: ToastBody) {
    this.messageService.add({
      severity: toastActionStatus.get(toastAction?.action!),
      detail: toastAction.message,
      summary: toastAction?.header,
      icon: toastAction.icon ? toastAction.icon : 'pi pi-info-circle'
    });
  }

  detailBuilder(toastAction: ToastBody) {
    if (toastAction.message) {
      return toastAction.message;
    }
    return toastAction.action === 0 ? 'toastMessage.success' : 'toastMessage.failed'

  }
}

const toastActionStatus: Map<number, string> = new Map([
  [0, 'success'],
  [1, 'error'],
  [2, 'warning']
])