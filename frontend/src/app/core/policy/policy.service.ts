import { Injectable } from '@angular/core';
import { constants } from '../common/constants';
import { UserDetailsData } from '../user/user.data.model';

@Injectable({
  providedIn: 'root',
})
export class PolicyService {
  policies: Set<string> = new Set<string>();
  constructor() { }

  init(): void {
    const userDetails: UserDetailsData = JSON.parse(localStorage.getItem(constants.USER_DETAILS)) as UserDetailsData;
    this.policies.clear();
    userDetails.policies.forEach(p => {
      this.policies.add(p.name.toLowerCase());
      p.verbs.forEach(v => {
        this.policies.add(v.toLowerCase() + ':' + p.name.toLowerCase());
      });
    });
  }

  hasPolicy(policy: string): boolean {
    return this.policies.has(policy);
  }

  hasAnyPolicy(policies: string[]): boolean {
    return policies.some(policy => this.policies.has(policy));
  }
}
