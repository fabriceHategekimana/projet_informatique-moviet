import { Component, OnInit } from '@angular/core'
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group'
import { ActivatedRoute, Router } from '@angular/router';
import { Tag, Tags } from '../../../shared/interfaces/tags'
import { Genre } from '../../../shared/interfaces/genre'
import { Keyword, KeywordResults } from '../../../shared/interfaces/keyword' // import keyword interface
import { MovieService } from '../../../services/movie.service'

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

  constructor(private movieService: MovieService, private groupsComponent : GroupsComponent, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.groupsComponent.getGroup( // get the group
      undefined,
      () => {
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        // console.log(this.currentGroup);
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
    // TODO: send preferences to the backend
    this.goToWaitPref();
  }
}
