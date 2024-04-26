import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse, HttpStatusCode } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { SpinnerService } from '../service/spinner.service';
import { TokenStorageService } from '../service/token-storage.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(
        private spinnerService: SpinnerService,
        private router: Router,
        private storageService: TokenStorageService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // this.spinnerService.requestStarted();
        return this.handler(request, next);
    }

    handler(request: HttpRequest<any>, next: HttpHandler) {
        return next.handle(request).pipe(
            tap({
                next: (event) => {
                    if (event instanceof HttpResponse) {
                        this.spinnerService.requestEnded();
                    }
                },
                error: (error: HttpErrorResponse) => {
                    this.spinnerService.resetSpinner();
                    if ([HttpStatusCode.Forbidden, HttpStatusCode.Unauthorized].indexOf(error.status) > -1) {
                        this.storageService.clearStorage();
                        this.router.navigate(['/login']).then(() => { });
                    }
                    throw error;
                }
            })
        );
    }
}