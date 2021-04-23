package domain.service;

import com.uwetrottmann.tmdb2.entities.Movie;
import domain.model.MovieDisplayInfo;

public interface MovieRequesterInterface {
    MovieDisplayInfo getDisplayInfo(int id);
}
