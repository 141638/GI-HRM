import { Component, Input, Output, EventEmitter, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DropDownRequest } from 'src/app/dto/request/dropdown-request';

@Component({
  selector: 'app-common-dropdown',
  templateUrl: './common-dropdown.component.html',
  styleUrls: ['./common-dropdown.component.scss']
})
export class CommonDropdownComponent implements OnInit {
  @Input() formGroup!: FormGroup;
  @Input() formName: string = '';
  @Input() showClear: boolean = false;
  @Input() filter: boolean = false;
  @Input() options: DropDownRequest[] = [];
  @Input() customLabel: boolean = false;
  @Input() optionValue: string | undefined = 'value';
  @Output() changeEmitter = new EventEmitter();
  @Output() clickEmitter = new EventEmitter();

  public selectedItem: any;
  constructor(private cdRef: ChangeDetectorRef) { }

  ngOnInit(): void { }

  ngAfterViewInit() {
    this.cdRef.detectChanges();
  }

  get form() {
    return this.formGroup.get(this.formName);
  }

  get selectedLabel() {
    return this.form ? this.options.filter(item => item.value === this.form!.value)[0].name : undefined;
  }

  emitChange($event: any) {
    this.changeEmitter.emit($event);
  }

  emitClick($event: any) {
    this.clickEmitter.emit($event);
  }
}
