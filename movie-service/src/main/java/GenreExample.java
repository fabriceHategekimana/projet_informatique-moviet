import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.GenreResults;
import com.uwetrottmann.tmdb2.services.GenresService;
import retrofit2.Response;

public class GenreExample {
    private static final String API_KEY = "1d667fd973287ca06ba66af7c1a33de0";

    public static void main(String[] args) {
        // Create an instance of the service you wish to use
        // you should re-use these
        Tmdb tmdb = new Tmdb(API_KEY);
        GenresService genreService = tmdb.genreService();
        // Call any of the available endpoints
        try {
            Response<GenreResults> response = genreService
                    .movie("fr")
                    .execute();
            if (response.isSuccessful()) {
                GenreResults genres = response.body();
                assert genres != null;
                assert genres.genres != null;
                System.out.println("Number of genres: " + genres.genres.size());
                for (int i = 0; i < 19; i++) {
                    System.out.println(genres.genres.get(i).name + " " + genres.genres.get(i).id);
                }
            }
        } catch (Exception e) {
            // see execute() javadoc
        }
    }
}
