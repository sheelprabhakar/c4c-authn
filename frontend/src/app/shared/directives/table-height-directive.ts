import { Directive, ElementRef, Renderer2, HostListener, AfterViewInit, Input, OnDestroy, } from '@angular/core';
import { Router, NavigationEnd, ResolveEnd } from '@angular/router';
import { filter, Subscription } from 'rxjs';

@Directive({
  selector: '[tableHeight]',
  standalone: true,
})
export class TableHeightDirective implements AfterViewInit, OnDestroy {
  @Input() minHeight: number;

  private routerSubscription: Subscription;
  constructor(private el: ElementRef, private renderer: Renderer2, private router: Router
  ) {

  }
  ngAfterViewInit() {
    this.setHeight();
    this.routerSubscription = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd || event instanceof ResolveEnd)
    ).subscribe(event => {
      console.log('Navigation or Resolve event:', event);
      this.setHeight();
    });
  }

  @HostListener('window:resize')
  onResize() {
    this.setHeight();
  }

  private setHeight() {
    const element = this.el.nativeElement.closest('.content-c4c');
    const viewportHeight = window.innerHeight;
    const elementOffsetTop = element.getBoundingClientRect().top;
    let height = viewportHeight - elementOffsetTop -285;

    if (this.minHeight && height < this.minHeight) {
      height = this.minHeight;
    }
    this.renderer.setStyle(this.el.nativeElement, 'height', `${height}px`);
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }
}
