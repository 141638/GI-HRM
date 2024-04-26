import { Injectable } from '@angular/core';
import {
    Router,
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
} from '@angular/router';
import { TokenStorageService } from '../service/token-storage.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private storageService: TokenStorageService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const currentUser = this.storageService.getUser();
        if (currentUser && currentUser.roles) {
            return true;
        }
        this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } })
        return false;
    }
}