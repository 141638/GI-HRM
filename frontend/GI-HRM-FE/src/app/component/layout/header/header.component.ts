import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CustomValidator } from 'src/app/common/validator';
import { SpinnerService } from 'src/app/service/spinner.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  public languages: any[] = [];
  public code: string = '';
  constructor(
    private storageService: TokenStorageService,
    public translate: TranslateService,
    public spinnerService: SpinnerService
  ) {
  }

  ngOnInit(): void {
    this.storageService.setLanguage

    let language = this.storageService.getLanguage();

    if (!CustomValidator.checkIsNullEmptyUndefined(language)) {
      if (this.translate.use(language.match(/en|vi/))) {
        this.code = language;
      } else {
        this.code = 'en';
      }
      this.translate.use(this.code);
    } else {
      this.defaultLang();
    }
    this.spinnerService.resetSpinner();
  }

  defaultLang() {
    this.translate.addLangs(['en', 'vi']);
    this.translate.setDefaultLang('en');
    let browserLang: any = this.translate.currentLang;
    if (browserLang === undefined) {
      browserLang = 'en';
    }
    this.storageService.setLanguage(browserLang);
    this.code = browserLang;
    this.translate.use(browserLang.match(/en|vi/) ? browserLang : 'en');
  }

  signOut(): void {
    this.storageService.signOut();
  }
  public languageItems = [
    {
      label: 'English',
      icon: 'flag flag-united-states',
      command: () => {
        this.switchLanguage('en');
      }
    },
    {
      label: 'Vietnamese',
      icon: 'flag flag-vietnam',
      command: () => {
        this.switchLanguage('vi');
      }
    }
  ];
  switchLanguage(language: string) {
    this.code = language;
    this.translate.use(this.code);
    this.storageService.setLanguage(this.code);
  }
}
