package domain.service;

import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import domain.model.DiscoverRequest;
import domain.model.MovieDisplayInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MovieRequesterInterface {
    MovieDisplayInfo getDisplayInfo(int id);
    List<Genre> getGenres();
    List<SortBy> getSortKeys();
    MovieResultsPage discover(DiscoverRequest request, @Nullable Integer page);
    // Todo add method that returns Movie with a representation specifically to be compared with other in the poll algorithm

}
