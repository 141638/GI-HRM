import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { eRole, HttpStatusCodeConstants } from 'src/app/common/common-enum';
import { CustomValidator } from 'src/app/common/validator';
import { LoginRequest } from 'src/app/dto/request/security/login-request';
import { ApiResponse } from 'src/app/dto/response/api-response';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { SpinnerService } from 'src/app/service/spinner.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  public loginForm: FormGroup = new FormGroup({});
  public termAndConditionDisplay: boolean = false;
  public privacyPolicyDisplay: boolean = false;
  public isSubmitted: boolean = false;
  public showPassword: boolean = false;
  public isLoginFailed: boolean = false;
  private httpStatus = HttpStatusCodeConstants;
  private loginRequest: LoginRequest = new LoginRequest();
  private loginResponse: ApiResponse = new ApiResponse();
  constructor(
    private spinnerService: SpinnerService,
    private authService: AuthenticationService,
    private router: Router,
    private storageService: TokenStorageService) { }
  ngOnInit() {
    this.checkLoggedIn()
    this.initForm();
  }

  checkLoggedIn(): void {
    const currentUser = this.storageService.getUser();
    if (currentUser && currentUser.roles) {
      this.redirectTo(currentUser.roles);
    }
  }

  initForm(): void {
    this.loginForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl(),
      terms: new FormControl(true)
    })
  }

  get control() {
    return this.loginForm.controls;
  }

  onLogin() {
    this.isSubmitted = true;
    this.setRequired();
    if (this.loginForm.invalid) {
      return;
    }
    this.loginRequest = this.loginForm.value;
    this.authService.login(this.loginRequest).subscribe({
      next: (response: ApiResponse) => {
        if (response.status === this.httpStatus.HTTP_STATUS_200) {
          this.loginResponse = response;
          let token = this.loginResponse.item.token;
          this.isLoginFailed = false;
          if (token) {
            this.storageService.saveToken(token);
            this.storageService.saveUser(this.loginResponse.item);
          }
          else {
            this.isLoginFailed = true;
          }
        }
        this.spinnerService.resetSpinner();
      },
      error: (error) => {
        this.spinnerService.resetSpinner();
        this.isLoginFailed = true;
      },
      complete: () => {
        let roles = this.storageService.getUser().roles;
        if (roles) {
          this.redirectTo(roles);
        }
      }
    });
  }
  setRequired() {
    CustomValidator.setValidatorFormGroup(this.loginForm, 'username');
    CustomValidator.setValidatorFormGroup(this.loginForm, 'password');
    CustomValidator.setValidatorFormGroup(this.loginForm, 'terms');
  }
  termUnderstand() {
    this.loginForm.controls['terms'].setValue(true);
    this.termAndConditionDisplay = false;
  }

  redirectTo(roles: string[]) {
    if (roles.indexOf(eRole.HR) >= 0) {
      this.router.navigate(["system/dashboard"]);
    }
    else if (roles.indexOf(eRole.EMPLOYEE) >= 0) {
      this.router.navigate(["user/dashboard"]);
    }
  }
}
