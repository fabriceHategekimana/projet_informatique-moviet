import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.WatchProviders;
import com.uwetrottmann.tmdb2.services.MoviesService;
import retrofit2.Response;

/*
https://developers.themoviedb.org/3/movies/get-movie-watch-providers
Powered by our partnership with JustWatch, you can query this method to get a list of the availabilities per country by provider.
        This is not going to return full deep links, but rather, it's just enough information to display what's available where.
        You can link to the provided TMDb URL to help support TMDb and provide the actual deep links to the content.

        Please note: In order to use this data you must attribute the source of the data as JustWatch.
                     If we find any usage not complying with these terms we will revoke access to the API.
*/

public class WatchProvidersExample {
    private static final String API_KEY = "fill me";

    public static void main(String[] args) {
        Tmdb tmdb = new Tmdb(API_KEY);
        int id = 299534;  // Movie "Avengers : Endgame" IMDB id
        String country = "FR";  // ISO 3166-1

        // !!! Should be gotten through configuration. DO NOT DO THIS. see DataExample.java
        String base_url = "https://image.tmdb.org/t/p"

        MoviesService moviesService = tmdb.moviesService();
        try {
            Response<WatchProviders> response = moviesService
                    .watchProviders(id)
                    .execute();
            if (response.isSuccessful()) {
                WatchProviders watchProviders = response.body();
                assert watchProviders != null;
//                System.out.println(watchProviders.results.get(country));
                System.out.println("Watch Providers:");
                System.out.println(watchProviders.results.get(country).link);
                System.out.println("No deep links nor price info :/");

                System.out.println("\t• Free:");
                for (WatchProviders.WatchProvider p: watchProviders.results.get(country).free) {
                    System.out.println("\t\t• " + p.provider_name + ": " + p.logo_path);
                }

                System.out.println("\t• Ads:");
                for (WatchProviders.WatchProvider p: watchProviders.results.get(country).ads) {
                    System.out.println("\t\t• " + p.provider_name + ": " + p.logo_path);
                }

                System.out.println("\t• Flatrate:");
                for (WatchProviders.WatchProvider p: watchProviders.results.get(country).flatrate) {
                    System.out.println("\t\t• " + p.provider_name + ": " + p.logo_path);
                }

                System.out.println("\t• Buy:");
                for (WatchProviders.WatchProvider p: watchProviders.results.get(country).buy) {
                    System.out.println("\t\t• " + p.provider_name + ": " + p.logo_path);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}