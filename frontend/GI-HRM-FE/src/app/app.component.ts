import { Component } from '@angular/core';
import { SpinnerService } from './service/spinner.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  title = 'GI-HRM-FE';
  constructor(private spinnerService: SpinnerService){

  }
  ngOnInit(){
  }
}
