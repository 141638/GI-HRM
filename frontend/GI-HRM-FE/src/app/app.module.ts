import { NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import {
  DialogService,
  DynamicDialogConfig,
  DynamicDialogRef,
} from 'primeng/dynamicdialog';
import { ConfirmationService } from 'primeng/api';
import { CurrencyPipe, DatePipe, DecimalPipe } from '@angular/common';
import { LoginComponent } from './security/login/login.component';
import { OtherComponent } from './component/layout/other/other.component';
import { TranslateModule } from '@ngx-translate/core';
import { TranslateLoader, TranslateService } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { CheckboxModule } from 'primeng/checkbox';
import { ForgotPasswordComponent } from './security/forgot-password/forgot-password.component';
import { DialogModule } from 'primeng/dialog';
import { PasswordModule } from 'primeng/password';
import { HomeComponent } from './component/layout/home/home.component';
import { ContentLayoutComponent } from './component/layout/content-layout/content-layout.component';
import { HeaderComponent } from './component/layout/header/header.component';
import { BadgeModule } from 'primeng/badge';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { DividerModule } from 'primeng/divider';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { HrmModule } from './hrm.module';
import { AuthInterceptor } from './security/auth.interceptor';
import { ErrorInterceptor } from './security/error.interceptor';
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
@NgModule({
  declarations: [
    AppComponent,
    SpinnerComponent,
    LoginComponent,
    OtherComponent,
    ForgotPasswordComponent,
    HomeComponent,
    ContentLayoutComponent,
    HeaderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    CardModule,
    InputTextModule,
    ButtonModule,
    ProgressSpinnerModule,
    CheckboxModule,
    DialogModule,
    PasswordModule,
    BadgeModule,
    AvatarModule,
    AvatarGroupModule,
    DividerModule,
    OverlayPanelModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    HrmModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    TranslateService,
    ConfirmationService,
    DynamicDialogConfig,
    DynamicDialogRef,
    DialogService,
    DatePipe,
    CurrencyPipe,
    DecimalPipe
  ],
  exports: [
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
})
export class AppModule { }
