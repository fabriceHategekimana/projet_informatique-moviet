import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component'
import { RegisterComponent } from './components/register/register.component'
import { GroupsComponent } from './components/groups/groups.component'
import { DisplayMovieComponent } from './shared/components/display-movie/display-movie.component' //*! DEBUG to remove later
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component'

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'login', component: LoginComponent },
  {path: 'register', component: RegisterComponent },
  {path: 'groups', component: GroupsComponent },
  {path: 'display', component: DisplayMovieComponent }, //*! DEBUG to remove later
  // error 404:
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
