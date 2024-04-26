import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environment/environment';
import { CommonService } from '../common/common.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DropdownService extends CommonService {
  private apiUrl = `${environment.apiUrl}resource/employee/`;
  private httpOptions = environment.httpOptions;

  constructor(private http: HttpClient) {
    super();
  }

  public getDropdownProject(): Observable<any> {
    const uri = this.paramBuilder(`${this.apiUrl}dropdown`, { name: 'url', value: 'project-all' });
    return this.http.get(uri, this.httpOptions);
  }

  getDropdownStaff(): Observable<any> {
    const uri = this.paramBuilder(`${this.apiUrl}dropdown`, { name: 'url', value: 'staff-all' });
    return this.http.get(uri, this.httpOptions);
  }
}
