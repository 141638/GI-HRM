import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenStorageService } from "../service/token-storage.service";
const TOKEN_HEADER_KEY = "Authorization";
const AUTH_TOKEN_PREFIX = "Bearer ";
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private tokenService: TokenStorageService) { }
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq = request;
        const token = this.tokenService.getToken();
        const language = this.tokenService.getLanguage();
        if (token != null) {
            authReq = request.clone({ headers: request.headers.set(TOKEN_HEADER_KEY, AUTH_TOKEN_PREFIX + token).set("Language", language) });
        }
        return next.handle(authReq);
    }
}