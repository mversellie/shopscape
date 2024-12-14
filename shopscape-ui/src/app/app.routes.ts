import {Routes} from '@angular/router';
import {HomePageComponent} from './home-page/home-page.component';
import {
  SidebarNavbarPageComponentComponent
} from './sidebar-navbar-page-component/sidebar-navbar-page-component.component';
import {LoginPageComponent} from './login-page/login-page.component';
import {AccountPageLayoutComponent} from './account-page-layout/account-page-layout.component';
import {ForgotPasswordPageComponent} from './forgot-password-page/forgot-password-page.component';
import {RegisterPageComponent} from './register-page/register-page.component';

export const routes: Routes = [
  { path: 'login', component: AccountPageLayoutComponent, children:[{path:'login',component: LoginPageComponent}]},
  { path: 'register', component: AccountPageLayoutComponent, children:[{path:'register',component: RegisterPageComponent}]},
  { path: 'forgot-password', component: AccountPageLayoutComponent,
    children:[{path:'forgot-password',component: ForgotPasswordPageComponent}]},
  { path: '', component: SidebarNavbarPageComponentComponent, children: [{ path: '', component: HomePageComponent}
    ]
  }
];

