import { Routes } from '@angular/router';
import {LayoutComponent} from "./shared/layout/layout.component";
import {DashboardComponent} from "./pages/dashboard/dashboard.component";
import {ManageUserComponent} from "./pages/manage-user/manage-user.component";
import {ManageTransactionComponent} from "./pages/manage-transaction/manage-transaction.component";
import {ManageAccountComponent} from "./pages/manage-account/manage-account.component";
import {ManageRoleComponent} from "./pages/manage-role/manage-role.component";
import {ManageCurrencyComponent} from "./pages/manage-currency/manage-currency.component";

export const routes: Routes = [

  {
    path: 'dashboard', component: LayoutComponent,
    children: [
      { path: '', component: DashboardComponent },
      { path: 'home', component: DashboardComponent },
      { path: 'user', component: ManageUserComponent },
      { path: 'transaction', component: ManageTransactionComponent },
      { path: 'account', component: ManageAccountComponent },
      { path: 'role', component:  ManageRoleComponent},
      { path: 'currency', component:  ManageCurrencyComponent},
    ],
    // canActivate: [authGuard]
  },
];
