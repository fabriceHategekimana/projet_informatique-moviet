package domain.service;

import retrofit2.Response;

import java.util.List;

public class ExempleMovieServiceRequester {
    public static void main(String[] args) {
        // Create an instance of the service you wish to use
        // you should re-use these
        MovietRequester moviet = new MovietRequester();
        MovieServiceRequesterInterface moviesService = moviet.movieRequester();
        // Call any of the available endpoints
        try {
            Integer page = 1;
            String sortBy = "POPULARITY_DESC";
            String release_year_gte = "1900";
            String release_year_lte = "2005";
            Integer[] genres = {28, 12};
            Integer[] keywords = {2964, 9951, 12988};
            Integer[] banned_genres = {10751};
            Integer[] banned_keywords = {14941};
            String genres_operator = "OR";
            String keywords_operator = "OR";
            Response<List<Integer>> response = moviesService
                    .discoverId(
                            page,
                            sortBy,
                            release_year_gte,
                            release_year_lte,
                            genres,
                            keywords,
                            banned_genres,
                            banned_keywords,
                            genres_operator,
                            keywords_operator)
                    .execute();
            // http://api/v1/movie-service/discover/id?page=1&sortBy=POPULARITY_DESC&release_year_gte=1900&release_year_lte=2005&genres=28&genres=12&banned_genres=10751&keywords=2964&keywords=9951&keywords=12988&banned_keywords=14941&genres_operator=OR&keywords_operator=OR

            if (response.isSuccessful()) {
                List<Integer> ids = response.body();
                assert ids != null;
                for (int id : ids) {
                    System.out.println(id);
                }
            } else {
                System.out.println("Wasn't a success :(");
            }
        } catch (Exception e) {
            // see execute() javadoc
        }
    }

}
