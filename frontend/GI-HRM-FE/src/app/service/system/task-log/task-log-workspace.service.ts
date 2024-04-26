import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonService } from 'src/app/common/common.service';
import { environment } from 'src/app/environment/environment';
@Injectable({
  providedIn: 'root',
})
export class TaskLogWorkspaceService extends CommonService {
  private apiUrl = `${environment.apiUrl}resource/system/tasklog/workspace/`;
  private httpOptions = environment.httpOptions;
  constructor(private http: HttpClient) {
    super();
  }

  search(request: any): Observable<any> {
    let paramName = { name: 'name', value: request.name };
    let paramProjectId = { name: 'projectId', value: request.projectId };
    let paramStaffId = { name: 'staffId', value: request.staffId };
    let uri = this.paramBuilder(`${this.apiUrl}search`, paramName, paramProjectId, paramStaffId);
    return this.getSSE(uri);
  }

  upsertWorkspace(request: any): Observable<any> {
    let uri = this.paramBuilder(`${this.apiUrl}upsert`);
    return this.http.put(uri, request);
  }

  dropdownTestSSE() {
    this.getSSE('http://localhost:8089/api/category/flux-stream-test').subscribe({
      next: (response) => console.log(response)
    })
  }
}
