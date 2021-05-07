package domain.service;

import com.uwetrottmann.tmdb2.entities.Genre;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import domain.model.DiscoverRequest;
import domain.model.MovieDisplayInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MovieRequesterInterface {
    MovieDisplayInfo getDisplayInfo(int id);
    List<Genre> getGenres();
    MovieResultsPage discover(DiscoverRequest request, @Nullable Integer page);

}
