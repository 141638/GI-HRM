import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmployeeAddRequest } from 'src/app/dto/request/system-staff/employee-add-request';
import { EmployeeListSearchRequest } from 'src/app/dto/request/system-staff/employee-list-search-request';
import { environment } from 'src/app/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeListService {
  private apiUrl = environment.apiUrl + 'resource/employee/';
  private httpOptions = environment.httpOptions;
  constructor(private http: HttpClient) { }

  public search(request: EmployeeListSearchRequest): Observable<any> {
    return this.http.post(this.apiUrl + 'search', request, this.httpOptions);
  }

  public addNewEmployee(employeeAddRequest: EmployeeAddRequest): Observable<any> {
    return this.http.post(this.apiUrl + 'add', employeeAddRequest, this.httpOptions);
  }

  delete(employeeId: number): Observable<any> {
    return this.http.delete(this.apiUrl + `delete?id=${employeeId}`, this.httpOptions);
  }
}
