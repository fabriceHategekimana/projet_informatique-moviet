package domain.helper;

import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import domain.model.MovieDisplayInfo;
import domain.service.MovieRequester;

import java.util.List;
import java.util.stream.Collectors;

public class MovieConverter {
    public static List<Integer> pageToIds(MovieResultsPage page){
        if (page.results == null) {
            return null;
        }
        return page.results.stream().map(result -> result.id).collect(Collectors.toList());
    }

    public static List<MovieDisplayInfo> pageToDisplayInfo(MovieResultsPage page){
        List<Integer> ids = pageToIds(page);
        if (ids == null) {
            return null;
        }
        MovieRequester movieRequester = new MovieRequester();
        return ids.stream().map(movieRequester::getDisplayInfo).collect(Collectors.toList());
    }
}
