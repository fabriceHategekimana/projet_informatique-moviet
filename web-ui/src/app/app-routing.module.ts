import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component'
import { RegisterComponent } from './components/register/register.component'
import { GroupsComponent } from './components/groups/groups.component'
import { DisplayMovieComponent } from './shared/components/display-movie/display-movie.component' //*! DEBUG to remove later
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component'
import { GroupInfoComponent } from './components/groups/group-info/group-info.component'
import { GroupGenresComponent } from './components/groups/group-genres/group-genres.component'
import { GroupJoinComponent } from './components/groups/group-join/group-join.component'
import { GroupFindMatchComponent } from './components/groups/group-find-match/group-find-match.component'
import { GroupWaitPreferencesComponent } from './components/groups/group-wait-preferences/group-wait-preferences.component'

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'login', component: LoginComponent },
  {path: 'register', component: RegisterComponent },
  {path: 'groups', component: GroupsComponent, // without parameters
    children: [
      {path: '', component: GroupJoinComponent, pathMatch: 'full'},
    ]
  }, 
  {path: 'groups/:groupId', component: GroupsComponent, // with parameters
    children: [
      {path: '', component: GroupInfoComponent, pathMatch: 'full'},
      {path: 'genres' , component: GroupGenresComponent},
      {path: 'wait-pref', component: GroupWaitPreferencesComponent},
      {path: 'find-match', component: GroupFindMatchComponent}
    ]
  },
  {path: 'display', component: DisplayMovieComponent }, //*! DEBUG to remove later
  // error 404:
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
