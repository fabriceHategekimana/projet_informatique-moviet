import { Component, OnInit } from '@angular/core'
import { GroupsComponent } from '../groups.component'
import { Group } from '../../../shared/interfaces/group'
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

  constructor(private movieService: MovieService, private groupsComponent : GroupsComponent) { }

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
    //TODO: update selection + remove case
    if (this.tags) {
      if (el.checked) {
        // check if the tag exist:
        let tagIndex = this.selectedTags.tags.findIndex((tag) => tag.name == name);
        if (tagIndex == -1) { // if the object doesn't exist
          this.selectedTags.tags.push({name: name, values: [value]}) // add the new object
          tagIndex = 0;
        } else {
          if (!this.selectedTags.tags[tagIndex].values.includes(value)) { // if it does not contain the value, we will add it
            this.selectedTags.tags[tagIndex].values.push(value);
          }
        }
      } else {
  
      }
    }
  }
}
