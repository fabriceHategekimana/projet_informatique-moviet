import { Component, OnInit } from '@angular/core'
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group'
import { ActivatedRoute, Router } from '@angular/router'
import { Tag, Tags } from '../../../shared/interfaces/tags'
import { Genre } from '../../../shared/interfaces/genre'
import { Keyword, KeywordResults } from '../../../shared/interfaces/keyword' // import keyword interface
import { MovieService } from '../../../services/movie.service'
import { UserStatusValue } from '../../../shared/interfaces/users-status'
import { UserService } from '../../../services/user.service'
import { GroupService } from 'src/app/services/group.service'
import { MoviePreferences } from '../../../shared/interfaces/movie-preferences'
import { User } from 'src/app/shared/interfaces/user'

@Component({
  selector: 'app-group-genres',
  templateUrl: './group-genres.component.html',
  styleUrls: ['./group-genres.component.css']
})
export class GroupGenresComponent implements OnInit {

  currentGroup?: Group;

  tags?: Tags;
  genres?: Genre[];
  selectedGenres: Genre[] = [];
  selectedTags: Tags = {tags: []};

  selectedKeywords: Keyword[] = [];
  proposedKeywords: Keyword[] = []; // proposed keywords from the query

  yearFrom?: number;
  yearTo?: number;

  keywordInput: string = ""; // input for the keywords

  isAdmin: boolean = false;

  myUser?: User;

  constructor(private movieService: MovieService, private userService: UserService, private groupService: GroupService, private groupsComponent : GroupsComponent, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.groupsComponent.getGroup( // get the group
      undefined,
      () => {
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        // console.log(this.currentGroup);
        this.getMyUser(() => {
          // Test if admin:
          this.testIfAdmin();
        });
      }
    );
    
    // get the tags
    this.getTags();

    this.getGenres();
  }

  getTags(): void {
    // subscribe to get the tags: async fct
    this.movieService.getTags()
        .subscribe(tags => this.tags = tags);
  }

  getGenres(): void {
    // subscribe to get the genres: async fct
    this.movieService.getGenres()
        .subscribe(genres => this.genres = genres);
  }

  getKeywords(input: string): void {
    // subscribe to get the keywords: async fct
    this.movieService.getKeywords(input)
        .subscribe(keywordResults => this.proposedKeywords = keywordResults['results']);
  }

  addKeyword(event: any) { // select a keyword and add it to the list
    let id = Number(event.currentTarget.getAttribute('keyword-id'));
    let name = event.currentTarget.getAttribute('keyword-name');
    if (this.selectedKeywords.findIndex((el) => el.id == id) == -1) { // if the keyword isn't in the list
      this.selectedKeywords.push({id: id, name: name})
      //* -----------------------------------------------
      // // remove keyword from the proposed keywords
      // let removeIndex = this.proposedKeywords.findIndex((k) => k.id == id);
      // if (removeIndex != -1) {
      //   this.proposedKeywords.splice(removeIndex, 1); // remove the keyword
      //   // this.getKeywords(this.keywordInput); // reset keywords
      // }
    }
    // console.log(id);
  }

  removeKeyword(event: any) { // remove a keyword from the list of the selected keywords
    let id = Number(event.currentTarget.getAttribute('keyword-id'));
    let name = event.currentTarget.getAttribute('keyword-name');
    let keywordIndex = this.selectedKeywords.findIndex((el) => el.id == id);
    if (keywordIndex != -1) { // if the keyword is in the list
      this.selectedKeywords.splice(keywordIndex, 1) // remove the keyword
    }
    // console.log(event.currentTarget)
  }

  checkBtn(el: any) { // check the checkbox and update selected tags
    el.checked = !el.checked;
    let name = el.name;
    let id = el.value;
    // console.log(el.checked)
    if (this.genres) {
      // check if the genre exist:
      let genreIndex = this.genres.findIndex((genre) => genre.id == id);
      if (el.checked) { // we want to add the genre
        if (genreIndex == -1) { // if the genre name doesn't exist
          this.selectedGenres.push({name: name, id: id}) // add the new genre object
        }
      } else { // we want to remove the genre
        if (genreIndex != -1) { // if the genre exist
          this.selectedGenres.splice(genreIndex, 1); // remove the genre
        }
      }
    }
    // console.log(this.selectedTags);
  }

  updateKeywords(event: any) { // call the fct when we type in the keyword input filed
    this.keywordInput = event.target.value;
    console.log(this.keywordInput);
    if (this.keywordInput.length != 0) { // if input is not empty
      this.getKeywords(this.keywordInput); // get the keywords
    } else {
      this.proposedKeywords = [];
    }
  }

  goToFindMatch() { // go to the find-match page
    this.router.navigate(['find-match'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  goToWaitPref() { // go to the wat-pref page
    this.router.navigate(['wait-pref'], {relativeTo: this.route.parent, skipLocationChange: true });
  }
  
  processPreferences() {
    // this function will send the preferences to the backend and then go to the find-match page
    // TODO: catch error
    if (this.currentGroup != undefined) {
      let test: number[] = this.selectedKeywords.map(k => k.id).filter(id => id != null) as number[];
      let preferences: MoviePreferences = {
        keywordsId: this.selectedKeywords.map(k => k.id).filter(id => id != null) as number[],
        genresId: this.selectedGenres.map(k => k.id),
        yearFrom: null,
        yearTo: null,}
      
      if (this.yearFrom != undefined) {
        preferences.yearFrom = this.yearFrom;
      }

      if (this.yearTo != undefined) {
        preferences.yearTo = this.yearTo;
      }

      this.groupService.sendPreferences(this.currentGroup.id, this.getMyUserId(), preferences);
      this.setUserStatus(() => {this.goToWaitPref();});
    }
  }

  setUserStatus(then: () => any = () => void 0, onError?: () => any) {
    this.groupsComponent.getGroup(undefined, () => {
        let groupId = this.groupsComponent.currentGroup!.id;
        this.groupService.getGroupStatus(groupId)
          .subscribe((groupStatus) => {
            if (groupStatus == UserStatusValue.CHOOSING) {
              this.groupService.setUserStatus(groupId, this.getMyUserId(), UserStatusValue.READY) // change status to READY
                .subscribe(()=> {
                  then();
                }, () => {
                  if (onError == undefined) {
                    then();
                  } else {
                    onError();
                  }
                }); 
            } else {
              if (onError == undefined) {
                then();
              } else {
                onError();
              }
            }
          }, () => {
            if (onError == undefined) {
              then();
            } else {
              onError();
            }
          });
    });
  }

  getMyUserId(): string {
    if (this.myUser != undefined) {
      return this.myUser.id;
    } else {
      return '';
    }
  }

  getMyUser(then: ()=>any = ()=>void 0) {
    this.userService.whoAmI()
      .subscribe((user) => {
        this.myUser = user;
        then();
      });
  }

  testIfAdmin() { // test if the user is admin
    if (this.currentGroup != undefined) {
      if (this.myUser != undefined) {
        if (this.myUser.id == this.currentGroup.admin_id) {
          this.isAdmin = true;
        } else {
          this.isAdmin = false;
        }
      }
    }
  }
}
