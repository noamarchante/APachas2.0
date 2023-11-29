import {ErrorHandler, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NotificationModule} from './modules/notification/notification.module';
import {SimpleNotificationsModule} from 'angular2-notifications';
import {ErrorNotificationHandler} from './modules/notification/handlers/error-notification.handler';
import {LoginComponent} from './components/authUser/login/login/login.component';
import {AuthenticationInterceptor} from './helpers/authentication.interceptor';
import {HomeComponent} from './components/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RegisterComponent} from "./components/authUser/register/register.component";
import {ListUsersComponent} from "./components/users/listUsers/listUsers.component";
import {CreateGroupComponent} from "./components/groups/createGroup/createGroup.component";
import {ListGroupsComponent} from "./components/groups/listGroups/listGroups.component";
import { NgSelectModule } from '@ng-select/ng-select';
import {DetailGroupComponent} from "./components/groups/detailGroup/detailGroup.component";
import {DetailUserComponent} from "./components/users/detailUser/detailUser.component";
import {MessageConfirmComponent} from "./components/confirm/messageConfirm.component";
import {ListEventsComponent} from "./components/events/listEvents/listEvents.component";
import {DetailEventComponent} from "./components/events/detailEvent/detailEvent.component";
import {CreateEventComponent} from "./components/events/createEvent/createEvent.component";
import {Daterangepicker} from "ng2-daterangepicker";
import {ListProductsComponent} from "./components/products/listProducts/listProducts.component";
import {CreateProductComponent} from "./components/products/createProduct/createProduct.component";
import {DetailProductComponent} from "./components/products/detailProduct/detailProduct.component";
import {CreateUserEventExpenseComponent} from "./components/products/createUserEventExpense/createUserEventExpense.component";
import {DetailProfileComponent} from "./components/authUser/profile/detailProfile/detailProfile.component";
import {ListTransactionsComponent} from "./components/transactions/listTransactions.component";
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import {LOCALE_ID } from '@angular/core';
import localeEs from '@angular/common/locales/es';
import {registerLocaleData} from "@angular/common";
import {EditProfileComponent} from "./components/authUser/profile/editProfile/editProfile.component";
import {VerifyEmailComponent} from "./components/authUser/email/verifyEmail/verifyEmail.component";
import {RetrievePasswordComponent} from "./components/authUser/email/retrievePassword/retrievePassword.component";
import {RetrievePasswordEmailComponent} from "./components/authUser/login/retrievePasswordEmail/retrievePasswordEmail.component";
import {TransactionHistoryComponent} from "./components/authUser/transactionHistory/transactionHistory.component";
import {NgxPayPalModule} from "ngx-paypal";
import {PaypalComponent} from "./components/authUser/paypal/paypal.component";

registerLocaleData(localeEs, 'es');
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ListUsersComponent,
    CreateGroupComponent,
    ListGroupsComponent,
    DetailGroupComponent,
    DetailUserComponent,
    MessageConfirmComponent,
    ListEventsComponent,
    DetailEventComponent,
    CreateEventComponent,
    CreateProductComponent,
    CreateUserEventExpenseComponent,
    ListProductsComponent,
    DetailProductComponent,
    ListTransactionsComponent,
    DetailProfileComponent,
    HomeComponent,
    EditProfileComponent,
    VerifyEmailComponent,
    RetrievePasswordComponent,
    RetrievePasswordEmailComponent,
    TransactionHistoryComponent,
    PaypalComponent
  ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        FormsModule,
        NgSelectModule,
        HttpClientModule,
        NotificationModule,
        BrowserAnimationsModule,
        CalendarModule.forRoot({
            provide: DateAdapter,
            useFactory: adapterFactory,
        }),
        SimpleNotificationsModule.forRoot({
            timeOut: 10000,
            preventDuplicates: true,
            pauseOnHover: true,
            clickToClose: true
        }),
        Daterangepicker,
        NgxPayPalModule
    ],

  providers: [
    {
      provide: ErrorHandler,
      useClass: ErrorNotificationHandler
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor, multi: true
    },
      {provide: LOCALE_ID, useValue: 'es'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
