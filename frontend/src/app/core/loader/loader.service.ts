import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoaderService {
  private loadingSubject = new BehaviorSubject<boolean>(false);
  loading$ = this.loadingSubject.asObservable();
  private timeoutId: any;

  show(timeout: number = 500): void {
    this.timeoutId = setTimeout(() => {
      this.loadingSubject.next(true);
    }, timeout);
  }

  hide(): void {
    clearTimeout(this.timeoutId);
    this.loadingSubject.next(false);
  }
}
