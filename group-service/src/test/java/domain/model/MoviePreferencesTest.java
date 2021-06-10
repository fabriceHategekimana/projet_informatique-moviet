package domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoviePreferencesTest {
    private Set<Integer> keywords_id;
    private Set<Integer> genres_id;
    private MoviePreferences movie_preferences;

    @BeforeEach
    public void setup(){
        keywords_id = new HashSet<>();
        genres_id = new HashSet<>();
        keywords_id.add(0);
        keywords_id.add(1);
        genres_id.add(2);
        genres_id.add(3);
        movie_preferences = new MoviePreferences(keywords_id, genres_id, 0, 1999);
    }


    @Test
    void testToString(){
        assertEquals("MoviePreferences(keywords_id=[0, 1], genres_id=[2, 3], year_from=0, year_to=1999)", movie_preferences.toString() );
    }

}
