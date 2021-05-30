import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/components/header/header.component';
import { FooterComponent } from './shared/components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { LoginComponent } from './components/login/login.component';
import { GroupsComponent } from './components/groups/groups.component';
import { RegisterComponent } from './components/register/register.component';
import { DisplayMovieComponent } from './shared/components/display-movie/display-movie.component';
import { GroupGenresComponent } from './components/groups/group-genres/group-genres.component';
import { GroupJoinComponent } from './components/groups/group-join/group-join.component';
import { GroupInfoComponent } from './components/groups/group-info/group-info.component';
import { GroupFindMatchComponent } from './components/groups/group-find-match/group-find-match.component';
import { GroupWaitPreferencesComponent } from './components/groups/group-wait-preferences/group-wait-preferences.component';
import { GroupWaitVotingComponent } from './components/groups/group-wait-voting/group-wait-voting.component';
import { DisplayUsersStatusComponent } from './shared/components/display-users-status/display-users-status.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    PageNotFoundComponent,
    LoginComponent,
    GroupsComponent,
    RegisterComponent,
    DisplayMovieComponent,
    GroupGenresComponent,
    GroupJoinComponent,
    GroupInfoComponent,
    GroupFindMatchComponent,
    GroupWaitPreferencesComponent,
    GroupWaitVotingComponent,
    DisplayUsersStatusComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [DisplayMovieComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
