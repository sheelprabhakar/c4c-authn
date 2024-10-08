import { HttpClient } from '@angular/common/http';
import {
  TRANSLOCO_LOADER,
  Translation,
  TranslocoLoader,
  TRANSLOCO_CONFIG,
  translocoConfig,
  TranslocoModule,
} from '@jsverse/transloco';
import { Injectable, NgModule } from '@angular/core';
import { environment } from '../environments/environment';
import { Observable } from 'rxjs';
import { ClientComponent } from './pages/client/client.component';
import { ClientDetailsComponent } from './pages/client/client-details/client-details.component';
import { C4cListComponent } from './shared/c4c-list/c4c-list.component';
import { RoleComponent } from './pages/role/role.component';
import { RoleDetailsComponent } from './pages/role/role-details/role-details.component';
import { RestAclComponent } from './pages/rest-acl/rest-acl.component';
import { RestAclDetailsComponent } from './pages/rest-acl/rest-acl-details/rest-acl-details.component';
import { LoaderComponent } from './shared/loader/loader.component';

@Injectable({ providedIn: 'root' })
export class TranslocoHttpLoader implements TranslocoLoader {
  constructor(private http: HttpClient) {}

  getTranslation(lang: string): Observable<Translation> {
    return this.http.get<Translation>(`/assets/i18n/${lang}.json`);
  }
}

@NgModule({
  exports: [TranslocoModule],
  providers: [
    {
      provide: TRANSLOCO_CONFIG,
      useValue: translocoConfig({
        availableLangs: ['de', 'en'],
        defaultLang: 'de',
        // Remove this option if your application doesn't support changing language in runtime.
        reRenderOnLangChange: true,
        prodMode: environment.production,
      }),
    },
    { provide: TRANSLOCO_LOADER, useClass: TranslocoHttpLoader },
  ],
  declarations: [
  ],
})
export class TranslocoRootModule {}
