import { Component, OnChanges, SimpleChanges, Input, Output, ChangeDetectorRef, EventEmitter } from '@angular/core';
import { ConfirmAction } from '../common-dto';
import { ConfirmationService } from 'primeng/api';
import { ConfirmActionType } from 'src/app/common/common-enum';

@Component({
  selector: 'app-common-confirm-dialog',
  templateUrl: './common-confirm-dialog.component.html',
  styleUrls: ['./common-confirm-dialog.component.scss']
})
export class CommonConfirmDialogComponent implements OnChanges {
  @Input() confirmAction: ConfirmAction | undefined;
  @Output() confirmEmitter = new EventEmitter();
  public icon: string = '';
  public header: string = '';
  public message: string = '';
  private defaultPropertiesMap: Map<number, ConfirmProperties> = new Map();
  constructor(private confirmationService: ConfirmationService, private cdRef: ChangeDetectorRef) {
    this.defaultPropertiesSetting();
  }

  defaultPropertiesSetting() {
    let defaultProps: ConfirmProperties = { header: 'confirmDialog.header.default', message: 'confirmDialog.message.default' };
    let defaultUpdateProps: ConfirmProperties = { header: 'confirmDialog.header.update', message: 'confirmDialog.message.update' };
    let defaultDeleteProps: ConfirmProperties = { header: 'confirmDialog.header.delete', message: 'confirmDialog.message.delete' };

    this.defaultPropertiesMap.set(ConfirmActionType.DEFAULT, defaultProps);
    this.defaultPropertiesMap.set(ConfirmActionType.UPDATE, defaultUpdateProps);
    this.defaultPropertiesMap.set(ConfirmActionType.DELETE, defaultDeleteProps);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.confirmAction?.action !== undefined) {
      this.mapActionsDTO(this.confirmAction);
    }
  }

  ngAfterViewInit() {
    this.cdRef.detectChanges();
  }

  mapActionsDTO(actionDTO: ConfirmAction) {
    let action = actionDTO?.action;
    let header = actionDTO?.header;
    let message = actionDTO?.message;
    let icon = actionDTO?.icon;

    if (!(header && message) && action) {
      this.setDefaultConfirmDialogByAction(action);
    } else {
      this.setConfirmDialogProperties(header, message, icon);
    }

    this.confirm();
  }

  setDefaultConfirmDialogByAction(action: number | undefined) {
    let defaultProps = this.defaultPropertiesMap.get(action!);
    this.setConfirmDialogProperties(defaultProps?.header, defaultProps?.message, defaultProps?.icon);
  }

  setConfirmDialogProperties(header: string | undefined, message: string | undefined, icon: string | undefined) {
    this.header = header!;
    this.message = message!;
    this.icon = icon!;
  }


  confirm() {
    this.confirmationService.confirm({
      accept: () => this.confirmEmitter.emit(true),
      reject: () => this.confirmEmitter.emit(false)
    });
  }
}

class ConfirmProperties {
  header: string | undefined;
  message: string | undefined;
  icon?: string = 'pi pi-exclamation-triangle';
}