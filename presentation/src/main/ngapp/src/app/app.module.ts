import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TranslateModule} from '@ngx-translate/core';
import 'hammerjs';

import {FuseModule} from '@fuse/fuse.module';

import {fuseConfig} from 'app/fuse-config';

import {AppComponent} from 'app/app.component';
import {LayoutModule} from 'app/layout/layout.module';
import {AppRoutingModule} from './app-routing.module';
import {LoginModule} from './login/login.module';
import {SharedModule} from './shared/shared.module';
import {ToastrModule} from 'ngx-toastr';
import {ErrorHandlerInterceptor} from './helpers/interceptors/error-handler-interceptor';
import {TokenInterceptor} from './helpers/interceptors/token-interceptor';


@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        HttpClientModule,
        AppRoutingModule,
        TranslateModule.forRoot(),
        FuseModule.forRoot(fuseConfig),
        SharedModule,
        LayoutModule,
        LoginModule,
        ToastrModule.forRoot({
            timeOut: 2000,
            positionClass: 'toast-top-right',
            preventDuplicates: false,
            progressBar: true,
            maxOpened: 10,
            tapToDismiss: true,
            newestOnTop: true
        }),
    ],
    providers: [
        [
            {provide: HTTP_INTERCEPTORS, useClass: ErrorHandlerInterceptor, multi: true},
            {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
        ]
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}
