import { ChangeDetectorRef, Component } from '@angular/core';
import { SpinnerService } from '../service/spinner.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.scss']
})
export class SpinnerComponent {
  public showSpinner: boolean = false;
  constructor(private spinnerService: SpinnerService, private cdRef: ChangeDetectorRef) {

  }

  ngOnInit(): void {
    this.init();
  }

  init(): void {
    this.spinnerService.getSpinnerObserver().subscribe((status) => {
      this.showSpinner = (status==='start');
      this.cdRef.detectChanges();
    })
  }
}
