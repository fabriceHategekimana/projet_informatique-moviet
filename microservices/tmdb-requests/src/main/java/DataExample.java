import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.AppendToResponseItem;
import com.uwetrottmann.tmdb2.services.ConfigurationService;
import com.uwetrottmann.tmdb2.services.MoviesService;
import retrofit2.Response;

import java.util.*;
import java.util.List;

public class DataExample {
    private static final String API_KEY = "fill me";  // secret! Do not share nor commit

    // DISCLAIMER: here the job is dirty, it's just an example of how the data is used.
    // Don't do it this dirty for on your own usages, refer to RequestExample.java for a bit cleaner request
    public static void main(String[] args) {

        Tmdb tmdb = new Tmdb(API_KEY);
        ConfigurationService configurationService = tmdb.configurationService();

        // need base url of video providers (YouTube and Vimeo)
        Map<String, String> video_providers_urls = new HashMap<>();
        video_providers_urls.put("YouTube", "https://www.youtube.com/watch?v=");
        video_providers_urls.put("Vimeo", "https://vimeo.com/");

        // necessary values to get images
        String base_url = null;  // "https://image.tmdb.org/t/p/"
        String backdrop_size = null;  // "w300" "w780" "w1280" "original"
        String poster_sizes = null;  // "w92" "w154" "w185" "w342" "w500" "w780" "original"
//        String still_sizes = null; // "w92" "w185" "w300" "original"
        try {
            Response<Configuration> response = configurationService
                    .configuration()
                    .execute();
            if (response.isSuccessful()) {
                Configuration configuration = response.body();
                assert configuration != null;
                assert configuration.images != null;
                base_url = configuration.images.secure_base_url;  // .secure_base_url or base_url (https vs http)
                assert configuration.images.backdrop_sizes != null;
                backdrop_size = configuration.images.backdrop_sizes.get(3);  // choose a size
                assert configuration.images.poster_sizes != null;
                poster_sizes = configuration.images.poster_sizes.get(6);  // choose a size
//                assert configuration.images.still_sizes != null;
//                still_sizes = configuration.images.still_sizes.get(6);  // choose a size
            }
        } catch (Exception e) {
            e.printStackTrace();  // Plz handle it better on real implementation
        }

        MoviesService moviesService = tmdb.moviesService();
        try {
            int id = 299534;  // Movie "Avengers : Endgame" IMDB id
            String language = "fr";  // [iso 639-1] !!! acts on the title, images, videos, reviews... !

            // This is for additional requests that normally would have been null
            // Most of these also have their dedicated method to be requested
            AppendToResponseItem[] items = {
                    AppendToResponseItem.IMAGES,
                    AppendToResponseItem.CHANGES,
                    AppendToResponseItem.MOVIE_CREDITS,
                    AppendToResponseItem.CREDITS,
                    AppendToResponseItem.VIDEOS,
                    AppendToResponseItem.ALTERNATIVE_TITLES,
                    AppendToResponseItem.KEYWORDS,
                    AppendToResponseItem.RECOMMENDATIONS,
                    AppendToResponseItem.RELEASE_DATES,
                    AppendToResponseItem.REVIEWS,
                    AppendToResponseItem.SIMILAR,
                    AppendToResponseItem.TRANSLATIONS,
                    AppendToResponseItem.LISTS,
                    AppendToResponseItem.EXTERNAL_IDS
            };
            AppendToResponse appendToResponse = new AppendToResponse(items);

            // Parameter appendToResponse, options are optional
            Response<Movie> response = moviesService
                    .summary(id, language, appendToResponse)
                    .execute();

            if (response.isSuccessful()) {
                Movie movie = response.body();
                assert movie != null;

                MovieExternalIds external_ids = movie.external_ids;
                String title = movie.title ;
                Date release_date = movie.release_date;
                String original_language = movie.original_language;
                Translations translations = movie.translations;
                Credits credits = movie.credits;
                List<Genre> genres = movie.genres;
                List<Integer> genre_ids = movie.genre_ids;
                Keywords keywords = movie.keywords;
                Images images = movie.images;
                Videos videos = movie.videos;

                Double popularity = movie.popularity;
                Integer rating = movie.rating;
                ReviewResultsPage reviews = movie.reviews;
                MovieResultsPage recommendations = movie.recommendations;

                assert external_ids != null;
                System.out.println("external_ids: ");
                System.out.println("\t• imdb_id: " + external_ids.imdb_id);
                System.out.println("\t• facebook_id: " + external_ids.facebook_id);
                System.out.println("\t• instagram_id: " + external_ids.instagram_id);
                System.out.println("\t• twitter_id: " + external_ids.twitter_id);

                System.out.println("title: " + title);
                System.out.println("release_date: " + release_date);
                System.out.println("original_language: " + original_language);

                System.out.println("translations: ");
                assert translations != null;
                assert translations.translations != null;
                for (int i = 7; i < 13; i++) {
                    Translations.Translation t = translations.translations.get(i);
                    System.out.println("\t• " + t.name);  // Also supports .english_name, iso_639_1 and iso_3166_1
                }
                System.out.println("\t• ...");

                assert credits != null;
                System.out.println("credits: ");
                System.out.println("\t• cast: ");
                assert credits.cast != null;
                for (int i = 0; i < 3; i++) {
                    CastMember c = credits.cast.get(i);
                    System.out.println("\t\t• " + c.name);
                }
                System.out.println("\t\t• ...");
                System.out.println("\t• crew: ");
                assert credits.crew != null;
                for (int i = 0; i < 3; i++) {
                    CrewMember c = credits.crew.get(i);
                    System.out.println("\t\t• " + c.name);
                }
                System.out.println("\t\t• ...");
                System.out.println("\t• guest_stars: " + credits.guest_stars);

                System.out.println("genres: ");
                assert genres != null;
                for (Genre g: genres) {
                    System.out.println("\t• " + g.name);
                }
                System.out.println("genre_ids: " + genre_ids); // ???

                System.out.println("keywords: ");
                assert Objects.requireNonNull(keywords).keywords != null;
                assert keywords.keywords != null;
                for (BaseKeyword k: keywords.keywords) {
                    System.out.println("\t• " + k.name);
                }

                assert images != null;
                // Images are accessed on : base_url + size + file_path
                System.out.println("images: " + images);
                System.out.println("\t• backdrops: ");
                assert images.backdrops != null;
                for (Image im: images.backdrops) {
                    System.out.println("\t• " + base_url + backdrop_size + im.file_path);
                }
                System.out.println("\t• posters: ");
                assert images.posters != null;
                for (Image im: images.posters) {
                    System.out.println("\t• " + base_url + poster_sizes + im.file_path);
                }
                System.out.println("\t• stills: " + images.stills);

                System.out.println("videos: ");
                assert Objects.requireNonNull(videos).results != null;
                assert videos.results != null;
                for (Videos.Video v: videos.results) {
                    System.out.println("\t• " + video_providers_urls.get(v.site) + v.key);
                }

                System.out.println("popularity: " + popularity);  // Some kind of score
                System.out.println("rating: " + rating); // rating given by the user of the current session id

                System.out.println("reviews: " + reviews);  // (here no result for language = "fr")
                assert reviews != null;
                assert reviews.results != null;
                for (Review r: reviews.results) {
                    System.out.println("\t• " + r.url);
                }

                System.out.println("recommendations: ");
                assert recommendations != null;
                assert recommendations.results != null;
                for (BaseMovie r: recommendations.results) {
                    System.out.println("\t• " + r.title);
                }

                System.out.println("See also .discover() method");
            }

        } catch (Exception e) {
            e.printStackTrace();  // Plz handle it better on real implementation
        }
    }
}
