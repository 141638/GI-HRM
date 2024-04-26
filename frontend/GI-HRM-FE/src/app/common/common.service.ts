import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { EventSourcePolyfill } from 'ng-event-source';
import { Observable } from 'rxjs';
import { LocalStorageKey } from './common-enum';

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  private DATE_FORMAT: string = "yyyy-MM-dd";
  constructor() { }


  protected dateToString(date: Date, format?: string) {
    const defaultLocale = 'en_EN';
    return new DatePipe(defaultLocale).transform(date, format ? format : this.DATE_FORMAT);
  }

  protected stringify(body: any) {
    return JSON.stringify(body);
  }

  protected convertRangeDateToRangeString(searchForm: FormGroup<any>, formControlName: string) {
    let rangeDateValues = searchForm.controls[formControlName].value;
    let convertedValues: string[] = [];
    if (rangeDateValues instanceof Array) {
      rangeDateValues.forEach((date: Date) => convertedValues.push(this.dateToString(date)!));
    }
    searchForm.controls[formControlName].setValue(convertedValues);
  }

  protected paramBuilder(url: string, ...params: { name: string, value: any }[]): string {
    url += '?';
    let hasUpdated = false;
    for (let item of params) {
      if (item.value !== undefined && item.value !== null && (typeof item.value !== 'string' || item.value.trim().length > 0)) {
        url += `${item.name}=${item.value}`;
        hasUpdated = true;
      }
    }

    if (!hasUpdated) {
      url = url.slice(0, url.length - 1);
    }

    return url;
  }

  protected getSSE(url: string): Observable<any> {
    const optionSSE = {
      headers: {
        'Authorization': `Bearer ${window.localStorage.getItem(LocalStorageKey.AUTH_TOKEN)}`
      }
    }
    return new Observable(observer => {
      let eventSource = new EventSourcePolyfill(url, optionSSE);
      eventSource.onmessage = event => observer.next(JSON.parse(event.data));
      eventSource.onerror = () => eventSource.close();
      return () => {
        eventSource.close();
      };
    });
  }
}
