import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { TrackOrderComponent } from './track-order/track-order.component';

const routes: Routes = [
  { path: "signup", component: SignupComponent},
  { path: "order", component: TrackOrderComponent},
  { path: 'customer', loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule) },
  { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) },
  { path: "login", component: LoginComponent},
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
