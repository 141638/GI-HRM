import { Component, Input, ViewChild, ElementRef, Renderer2, ChangeDetectorRef } from '@angular/core';
import { DropDownRequest } from 'src/app/dto/request/dropdown-request';

@Component({
  selector: 'app-common-image',
  templateUrl: './common-image.component.html',
  styleUrls: ['./common-image.component.scss']
})

export class CommonImageComponent {
  @Input() width!: number;
  @Input() height!: number;
  @Input() source!: string;
  @Input() rounded: boolean = false;
  @Input() avatarRank: number = -1;
  @Input() type: string | undefined;
  @ViewChild('imageCover') imageCover!: ElementRef;
  @ViewChild('image') image!: ElementRef;
  @ViewChild('avatarFrame') avatarFrame!: ElementRef;
  constructor(private renderer: Renderer2, private cdRef: ChangeDetectorRef) { }
  ngAfterViewChecked() {
    this.setImageSize();
    this.cdRef.detectChanges();
  }
  avatarRankSet: any[] = [
    { value: -1, url: '../../../../assets/img/avatar_frame/defaultRank.png' },
    { value: 0, url: '../../../../assets/img/avatar_frame/superAdminRank.png' },
    { value: 1, url: '../../../../assets/img/avatar_frame/adminRank.png' },
    { value: 2, url: '../../../../assets/img/avatar_frame/employeeRank.png' },
    { value: 3, url: '../../../../assets/img/avatar_frame/defaultRank.png' },
  ];
  get avartarFrameSrc() {
    return this.avatarRankSet.filter(item => item.value === this.avatarRank)[0]?.url;
  }
  setImageSize() {
    let imageWidth, imageHeight, frameWidth, frameHeight;
    if (this.type?.match('avatar')) {
      imageWidth = '3rem';
      imageHeight = '3rem';
      frameWidth = '4rem';
      frameHeight = '4rem';
      this.renderer.setStyle(this.avatarFrame.nativeElement, 'width', frameWidth);
      this.renderer.setStyle(this.avatarFrame.nativeElement, 'height', frameHeight);
      if (!this.source) {
        this.source = "../../../../assets/img/no-avatar.jpg";
      }
    } else {
      imageWidth = this.width + 'rem';
      imageHeight = this.height + 'rem';
    }
    this.renderer.setStyle(this.imageCover.nativeElement, 'width', imageWidth);
    this.renderer.setStyle(this.imageCover.nativeElement, 'height', imageHeight);
    this.renderer.setStyle(this.image.nativeElement, 'width', 'inherit');
    this.renderer.setStyle(this.image.nativeElement, 'height', 'inherit');
  }
}
