import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../dto/request/security/login-request';
import { environment } from '../environment/environment';
import { Observable } from 'rxjs'

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private apiUrl = environment.apiUrl + 'auth/';
  private httpOptions = environment.httpOptions;

  constructor(private http: HttpClient) { }

  login(loginRequest: LoginRequest): Observable<any> {
    return this.http.post(this.apiUrl + 'login', loginRequest, this.httpOptions);
  }

  logout(token: string | null): Observable<any> {
    return this.http.post(this.apiUrl + 'logout', this.httpOptions);
  }
}
