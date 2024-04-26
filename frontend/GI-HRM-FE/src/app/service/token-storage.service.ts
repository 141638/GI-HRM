import { EventEmitter, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ApiResponse } from '../dto/response/api-response';
import { SpinnerService } from '../service/spinner.service';
import { AuthenticationService } from './authentication.service';
import { CookieService } from 'ngx-cookie-service';
const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const LANGUAGE = 'language';
@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  sub = new EventEmitter();

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private spinnerService: SpinnerService,
    private cookieService: CookieService
  ) { }

  signOut() {
    const token = this.getToken();
    this.authService.logout(token).subscribe({
      next: (response: ApiResponse) => {
        this.spinnerService.resetSpinner();
        this.clearStorage();
        this.router.navigate(['/login']);
      },
      error: () => {
        this.clearStorage();
        this.router.navigate(['/login']);
      }
    })
  }

  public saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.localStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.localStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return {};
  }

  public setLanguage(language: string): any {
    window.localStorage.removeItem(LANGUAGE);
    window.localStorage.setItem(LANGUAGE, language);
  }

  public getLanguage(): any {
    const language = window.localStorage.getItem(LANGUAGE);
    if (language) {
      return language;
    }
    return null;
  }

  public saveCacheSearch(screen: string, cache: any) {
    window.localStorage.setItem(screen, JSON.stringify(cache));
  }

  public getCacheSearch(screen: string) {
    return JSON.parse(window.localStorage.getItem(screen) as string);
  }

  public clearCacheSearch(screen: string) {
    window.localStorage.removeItem(screen);
  }

  // public removeTokenWhenChangePass() {
  //   window.localStorage.clear();
  //   // window.sessionStorage.removeItem(LANGUAGE);
  //   window.localStorage.removeItem(LANGUAGE);
  // }

  public setLanguageDropdown(data: any) {
    this.sub.emit(data);
  }

  public clearAll() {
    window.localStorage.clear();
  }

  public clearStorage() {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.removeItem(LANGUAGE);
  }

  copyTokenToCookie() {
    this.cookieService.set('auth-token', JSON.stringify({token: this.getToken()!}), { expires: 1 });
  }
}
