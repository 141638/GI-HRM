import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OtherComponent } from './component/layout/other/other.component';
import { LoginComponent } from './security/login/login.component';
import { TranslateModule } from '@ngx-translate/core';
import { ForgotPasswordComponent } from './security/forgot-password/forgot-password.component';
import { NavChildrenRouter, NavRouter } from './common/common-enum';
import { ContentLayoutComponent } from './component/layout/content-layout/content-layout.component';
import { HrmSystemModule } from './component/content/hrm-system/hrm-system.module'
import { AuthGuard } from './security/auth-guard';

const routes: Routes = [
  { path: '', redirectTo: NavRouter.LOGIN, pathMatch: 'full' },
  {
    path: '', component: ContentLayoutComponent, children: [
      {
        path: NavChildrenRouter.HRM_SYSTEM,
        loadChildren: () => import('./component/content/hrm-system/hrm-system.module').then((m) => m.HrmSystemModule)
      },
      {
        path: NavChildrenRouter.HRM_USER,
        loadChildren: () => import('./component/content/hrm-user/hrm-user.module').then((m) => m.HrmUserModule)
      }
    ],
    canActivate: [AuthGuard]
  },
  { path: NavRouter.LOGIN, component: LoginComponent },
  { path: NavRouter.FORGOT_PASSWORD, component: ForgotPasswordComponent },
  { path: NavRouter.OTHER, component: OtherComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule, TranslateModule]
})
export class AppRoutingModule { }
