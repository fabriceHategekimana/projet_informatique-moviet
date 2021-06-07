import com.uwetrottmann.tmdb2.DiscoverMovieBuilder;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.DiscoverFilter;
import com.uwetrottmann.tmdb2.entities.MovieResultsPage;
import com.uwetrottmann.tmdb2.entities.TmdbDate;
import com.uwetrottmann.tmdb2.enumerations.SortBy;
import com.uwetrottmann.tmdb2.services.DiscoverService;
import retrofit2.Call;
import retrofit2.Response;

public class DiscoverExample {
    private static final String API_KEY = "FILL_ME";

    public static void main(String[] args) {
        // Create an instance of the service you wish to use
        // you should re-use these
        Tmdb tmdb = new Tmdb(API_KEY);
        DiscoverService discoverService = tmdb.discoverService();
        DiscoverMovieBuilder discoverMovieBuilder = new DiscoverMovieBuilder(discoverService);
        // Call any of the available endpoints
        try {
            /*
             * <p>Discover movies by different types of data like average rating, number of votes, genres and certifications.<br>
             * You can get a valid list of certifications from the /certifications method.</p>
             * <p>Discover also supports a nice list of sort options. See below for all of the available options.</p>
             * <p>Please note, when using certification \ certification.lte you must also specify certification_country.<br>
             * These two parameters work together in order to filter the results. You can only filter results with the countries we have added to our certifications list.</p>
             * <p>If you specify the region parameter, the regional release date will be used instead of the primary release date.<br>
             * The date returned will be the first date based on your query (ie. if a with_release_type is specified).<br>
             * It's important to note the order of the release types that are used.<br>
             * Specifying "2|3" would return the limited theatrical release date as opposed to "3|2" which would return the theatrical date.</p>
             *
             * @param language                 <em>Optional.</em> ISO 639-1 code.
             * @param region                   <em>Optional.</em> ISO 3166-1 code.
             * @param sort_by                  <em>Optional.</em> <b>Allowed Values:</b> , popularity.asc, popularity.desc, release_date.asc, release_date.desc, revenue.asc, revenue.desc, primary_release_date.asc, primary_release_date.desc, original_title.asc, original_title.desc, vote_average.asc, vote_average.desc, vote_count.asc, vote_count.desc
             *                                 <b>Default:</b> popularity.desc
             * @param certification_country    <em>Optional.</em> Used in conjunction with the certification filter, use this to specify a country with a valid certification.
             * @param certification            <em>Optional.</em> Filter results with a valid certification from the 'certification_country' field.
             * @param certification_lte        <em>Optional.</em> Filter and only include movies that have a certification that is less than or equal to the specified value.
             * @param include_adult            <em>Optional.</em> A filter and include or exclude adult movies. Expected values: true/false.
             * @param include_video            <em>Optional.</em> A filter to include or exclude videos. Expected Values: true/false.
             * @param page                     <em>Optional.</em> Minimum value is 1, expected value is an integer.
             * @param primary_release_year     <em>Optional.</em> A filter to limit the results to a specific primary release year.
             * @param primary_release_date_gte <em>Optional.</em> Filter and only include movies that have a primary release date that is greater or equal to the specified value.
             * @param primary_release_date_lte <em>Optional.</em> Filter and only include movies that have a primary release date that is less than or equal to the specified value.
             * @param release_date_gte         <em>Optional.</em> Filter and only include movies that have a release date (looking at all release dates) that is greater or equal to the specified value.
             * @param release_date_lte         <em>Optional.</em> Filter and only include movies that have a release date (looking at all release dates) that is less than or equal to the specified value.
             * @param vote_count_gte           <em>Optional.</em> Filter and only include movies that have a vote count that is greater or equal to the specified value.
             * @param vote_count_lte           <em>Optional.</em> Filter and only include movies that have a vote count that is less than or equal to the specified value.
             * @param vote_average_gte         <em>Optional.</em> Filter and only include movies that have a rating that is greater or equal to the specified value.
             * @param vote_average_lte         <em>Optional.</em> Filter and only include movies that have a rating that is less than or equal to the specified value.
             * @param with_cast                <em>Optional.</em> Only include movies that have one of the ID's added as an actor.
             * @param with_crew                <em>Optional.</em> Only include movies that have one of the ID's added as a crew member.
             * @param with_companies           <em>Optional.</em> Only include movies that have one of the ID's added as a production company.
             * @param with_genres              <em>Optional.</em> Only include movies that have one of the ID's added as a genre.
             * @param with_keywords            <em>Optional.</em> Only include movies that have one of the ID's added as a keyword.
             * @param with_people              <em>Optional.</em> Only include movies that have one of the ID's added as a either a actor or a crew member.
             * @param year                     <em>Optional.</em> A filter to limit the results to a specific year (looking at all release dates).
             * @param without_genres           <em>Optional.</em> Genre ids that you want to exclude from the results.
             * @param with_runtime_gte         <em>Optional.</em> Filter and only include movies that have a runtime that is greater or equal to a value.
             * @param with_runtime_lte         <em>Optional.</em> Filter and only include movies that have a runtime that is less than or equal to a value.
             * @param with_release_type        <em>Optional.</em> These release types map to the same values found on the movie release date method.
             * @param with_original_language   <em>Optional.</em> ISO 639-1 string to filter results by their original language value.
             * @param without_keywords         <em>Optional.</em> Exclude items with certain keywords.
             * @see <a href="https://developers.themoviedb.org/3/discover/movie-discover">Movie Discover</a>
             */
            Call<MovieResultsPage> call = discoverMovieBuilder
//                    .language()
//                    .region()
                    .sort_by(SortBy.POPULARITY_DESC)                        // ~+
//                    .certification_country()
//                    .certification()
//                    .certification_lte()
//                    .includeAdult()
//                    .includeVideo()
                    .page(1)                           // !
//                    .primary_release_year()
//                    .primary_release_date_gte()
//                    .primary_release_date_lte()
                    .release_date_gte(new TmdbDate("2000-01-01"))               // !
                    .release_date_lte(new TmdbDate("2015-01-01"))               // !
//                    .vote_count_gte()                 // ~
//                    .vote_count_lte()                 // ~
//                    .vote_average_gte()               // ~
//                    .vote_average_lte()               // ~
//                    .with_cast()                      // ~
//                    .with_crew()                      // ~
//                    .with_companies()                 // ~
                    .with_genres(new DiscoverFilter(28, 12))                    // !
                    .with_keywords(new DiscoverFilter(DiscoverFilter.Separator.OR, 9663, 177912, 12988))                  // !
//                    .with_people()                    // ~
//                    .year()                           // ~
//                    .without_genres()                 // !
//                    .with_runtime_gte()               // ~+
//                    .with_runtime_lte()               // ~+
//                    .with_release_type()
//                    .with_original_language()         // ~+
//                    .without_keywords()               // !
                    .build();
            Response<MovieResultsPage> response = call.execute();

            if (response.isSuccessful()) {
                MovieResultsPage movieResultsPage = response.body();
                assert movieResultsPage != null;
                System.out.println("Total Results: " + movieResultsPage.total_results);

                System.out.println("Page " + movieResultsPage.page + " out of " + movieResultsPage.total_pages);
                assert movieResultsPage.total_results != null;
                int n = (10 <= movieResultsPage.total_results) ? 10 : movieResultsPage.total_results;
                if (movieResultsPage.results != null) {
                    for (int i = 0; i < n; i++) {
                        System.out.println(movieResultsPage.results.get(i).title);
                    }
                }
            }
        } catch (Exception e) {
            // see execute() javadoc
        }
    }
}
