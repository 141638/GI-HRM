import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { CheckboxModule } from 'primeng/checkbox';
import { PaginatorModule } from 'primeng/paginator';
import { TableModule } from 'primeng/table';
import { RadioButtonModule } from 'primeng/radiobutton';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { TabViewModule } from 'primeng/tabview';
import { DynamicDialogConfig, DynamicDialogModule, DynamicDialogRef } from 'primeng/dynamicdialog';
import { DividerModule } from 'primeng/divider';
import { HttpClient } from '@angular/common/http';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { ImageModule } from 'primeng/image';
import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';
import { FileUploadModule } from 'primeng/fileupload';
import { KeyFilterModule } from 'primeng/keyfilter';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { PanelModule } from 'primeng/panel';
import { CardModule } from 'primeng/card';
import { ChartModule } from 'primeng/chart';
import { ProgressBarModule } from 'primeng/progressbar';
import { CommonComponent } from './component/common/common-component/common.component';
import { CommonImageComponent } from './component/common/common-image/common-image.component';
import { CommonDropdownComponent } from './component/common/common-dropdown/common-dropdown.component';
import { MenuModule } from 'primeng/menu';
import { CommonToastComponent } from './component/common/common-toast/common-toast.component';
import { MessageService } from 'primeng/api';
import { SkeletonModule } from 'primeng/skeleton';
import { RippleModule } from 'primeng/ripple';
import { PanelMenuModule } from 'primeng/panelmenu';
import { CommonConfirmDialogComponent } from './component/common/common-confirm-dialog/common-confirm-dialog.component';
import { MultiSelectModule } from 'primeng/multiselect';
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
@NgModule({
  declarations: [
    CommonComponent,
    CommonImageComponent,
    CommonDropdownComponent,
    CommonToastComponent,
    CommonConfirmDialogComponent
  ],
  imports: [
    CommonModule,
    DividerModule,
    CalendarModule,
    DropdownModule,
    InputTextModule,
    CheckboxModule,
    ReactiveFormsModule,
    ConfirmDialogModule,
    ToastModule,
    FormsModule,
    PaginatorModule,
    TableModule,
    RadioButtonModule,
    AutoCompleteModule,
    TabViewModule,
    DynamicDialogModule,
    ImageModule,
    AvatarModule,
    AvatarGroupModule,
    FileUploadModule,
    KeyFilterModule,
    InputTextareaModule,
    TranslateModule.forChild({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    PanelModule,
    CardModule,
    ChartModule,
    ProgressBarModule,
    MenuModule,
    SkeletonModule,
    RippleModule,
    
  ],
  exports: [
    CommonModule,
    DividerModule,
    CalendarModule,
    DropdownModule,
    InputTextModule,
    CheckboxModule,
    ReactiveFormsModule,
    ConfirmDialogModule,
    ToastModule,
    FormsModule,
    PaginatorModule,
    TableModule,
    RadioButtonModule,
    AutoCompleteModule,
    TabViewModule,
    DynamicDialogModule,
    ImageModule,
    AvatarModule,
    AvatarGroupModule,
    FileUploadModule,
    KeyFilterModule,
    InputTextareaModule,
    TranslateModule,
    PanelModule,
    CardModule,
    ChartModule,
    ProgressBarModule,
    SkeletonModule,
    RippleModule,
    MenuModule,
    PanelMenuModule,
    MultiSelectModule,

    CommonImageComponent,
    CommonDropdownComponent,
    CommonToastComponent,
    CommonConfirmDialogComponent
  ],
  providers: [TranslateService, DynamicDialogConfig, DynamicDialogRef, MessageService],
})
export class HrmModule { }
