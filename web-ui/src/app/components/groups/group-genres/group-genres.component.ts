import { Component, OnInit } from '@angular/core'
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group'
import { ActivatedRoute, Router } from '@angular/router';
import { Tag, Tags } from '../../../shared/interfaces/tags'
import { MovieService } from '../../../services/movie.service'

@Component({
  selector: 'app-group-genres',
  templateUrl: './group-genres.component.html',
  styleUrls: ['./group-genres.component.css']
})
export class GroupGenresComponent implements OnInit {

  currentGroup?: Group;
  tags?: Tags;
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
  }

  getTags(): void {
    // subscribe to get the tags: async fct
    this.movieService.getTags()
        .subscribe(tags => this.tags = tags);
  }

  checkBtn(el: any) { // check the checkbox and update selected tags
    el.checked = !el.checked;
    let name = el.name;
    let value = el.value;
    console.log(el.checked)
    if (this.tags) {
      // check if the tag exist:
      let tagIndex = this.selectedTags.tags.findIndex((tag) => tag.name == name);
      if (el.checked) { // we want to add the tag
        if (tagIndex == -1) { // if the tagname doesn't exist
          this.selectedTags.tags.push({name: name, values: [value]}) // add the new object
        } else { // tag name already exist
          if (!this.selectedTags.tags[tagIndex].values.includes(value)) { // if it does not contain the value, we will add it
            this.selectedTags.tags[tagIndex].values.push(value);
          }
        }
      } else { // we want to remove the tag
        if (tagIndex != -1) { // if the tagname exist
          let valueIndex = this.selectedTags.tags[tagIndex].values.findIndex((tagValue) => tagValue == value); // add the new object
          if (valueIndex != -1) { // if value exist
            // remove the value:
            this.selectedTags.tags[tagIndex].values.splice(valueIndex, 1); // remove the value
            // if the tag values is empty, we remove the tag from the lis:
            if (this.selectedTags.tags[tagIndex].values.length == 0) {
              this.selectedTags.tags.splice(tagIndex, 1); // remove the tag
            }
          }
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
