import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Movie;
import com.uwetrottmann.tmdb2.services.MoviesService;
import retrofit2.Response;

public class RequestExample {
    private static final String API_KEY = "1d667fd973287ca06ba66af7c1a33de0";

    public static void main(String[] args) {
        // Create an instance of the service you wish to use
        // you should re-use these
        Tmdb tmdb = new Tmdb(API_KEY);
        MoviesService moviesService = tmdb.moviesService();
        // Call any of the available endpoints
        try {
            Response<Movie> response = moviesService
                    .summary(550, "fr")
                    .execute();
            if (response.isSuccessful()) {
                Movie movie = response.body();
                assert movie != null;
                System.out.println(movie.title + " is awesome!");
            }
        } catch (Exception e) {
            // see execute() javadoc
        }
    }
}
