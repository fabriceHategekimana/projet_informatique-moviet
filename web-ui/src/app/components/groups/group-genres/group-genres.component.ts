import { Component, OnInit } from '@angular/core'
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group'
import { ActivatedRoute, Router } from '@angular/router';
import { Tag, Tags } from '../../../shared/interfaces/tags'
import { Genre } from '../../../shared/interfaces/genre'
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

  constructor(private movieService: MovieService, private groupsComponent : GroupsComponent, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.groupsComponent.getGroup( // get the group
      undefined,
      () => {
        this.currentGroup = this.groupsComponent.currentGroup; // save the current group
        console.log(this.currentGroup);
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

  checkBtn(el: any) { // check the checkbox and update selected tags
    el.checked = !el.checked;
    let name = el.name;
    let id = el.value;
    console.log(el.checked)
    if (this.genres) {
      // check if the genre exist:
      let genreIndex = this.genres.findIndex((genre) => genre.name == name);
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
    console.log(this.selectedTags);
  }

  goToFindMatch() { // go to the find-match page
    this.router.navigate(['find-match'], {relativeTo: this.route.parent, skipLocationChange: true });
  }

  
  processPreferences() {
    // this function will send the preferences to the backend and then go to the find-match page
    // TODO: send preferences to the backend
    this.goToFindMatch();
  }
}
