import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';

import { AppHttpInterceptorService } from './http-interceptor.service';
import { HomepageComponent } from './homepage/homepage.component';
import { ViewSlatesComponent } from './view-slates/view-slates.component';
import { CreateSlateComponent } from './create-slate/create-slate.component';
import {VotingModule} from "./voting/voting.module";
import {VotingComponent} from "./voting/voting/voting.component";

const routes: Routes = [
  {
    path: 'home',
    component: HomepageComponent,
    data: {}
  },
  {
    path: 'createSlate',
    component: CreateSlateComponent,
    data: {}
  },
  {
    path: 'viewSlates',
    component: ViewSlatesComponent,
    data: {}
  },
  {
    path: 'vote/:slateID',
    component: VotingComponent,
    data: {}
  },
  {
    path: '**',
    redirectTo: '/home',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    ViewSlatesComponent,
    CreateSlateComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'Csrf-Token',
      headerName: 'Csrf-Token',
    }),
    RouterModule.forRoot(routes),
    ReactiveFormsModule,
    VotingModule
  ],
  providers: [
    {
      multi: true,
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptorService
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
