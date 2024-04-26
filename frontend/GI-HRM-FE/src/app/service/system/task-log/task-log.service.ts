import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/app/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class TaskLogService {
  private apiUrl = "http://localhost:8089/api/category/";
  private httpOptions = environment.httpOptions;

  constructor(private http: HttpClient) { }

  public upsertCategory(request: any): Observable<any> {
    return this.http.put(`${this.apiUrl}upsert`, request, this.httpOptions);
  }
}
