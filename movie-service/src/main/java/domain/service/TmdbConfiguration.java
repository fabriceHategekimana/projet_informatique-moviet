package domain.service;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.Configuration;
import com.uwetrottmann.tmdb2.services.ConfigurationService;
import retrofit2.Response;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TmdbConfiguration implements TmdbConfigurationInterface {
    private static final Logger LOGGER = Logger.getLogger(TmdbConfiguration.class.getName());

    private final static TmdbConfiguration instance = new TmdbConfiguration();
    private final Tmdb tmdb;
    private final String BASE_URL;

    private TmdbConfiguration() {
        String API_KEY = initApiKey();
        tmdb = new Tmdb(API_KEY);
        BASE_URL = initBaseUrl();
    }

    public static TmdbConfiguration getInstance() {
        return instance;
    }

    @Override
    public Tmdb getTmdb() {
        return tmdb;
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    private String initApiKey() {
        // TODO implement (when secrets are available)
        return "api_key";
    }

    private String initBaseUrl() {
        String BaseUrl = null;

        ConfigurationService configurationService = this.tmdb.configurationService();

        try {
            Response<Configuration> response = configurationService
                    .configuration()
                    .execute();
            if (response.isSuccessful()) {
                Configuration configuration = response.body();

                if (configuration != null && configuration.images != null) {
                    BaseUrl = configuration.images.secure_base_url;  // .secure_base_url or base_url (https vs http)
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Couldn't retrieve Tmdb base url", e);
        }

        return BaseUrl;
    }
}
