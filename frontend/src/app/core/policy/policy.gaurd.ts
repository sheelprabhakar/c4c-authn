import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { PolicyService } from './policy.service';

@Injectable({
  providedIn: 'root'
})
export class PolicyGuard implements CanActivate {
  constructor(private policyService: PolicyService) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const allowedPolicy = next.data.policies as Array<string>;
    return this.policyService.hasAnyPolicy(allowedPolicy);
  }
}
