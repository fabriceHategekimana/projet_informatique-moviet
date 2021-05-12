package domain.service;

import com.uwetrottmann.tmdb2.Tmdb;

public interface TmdbConfigurationInterface {

    static TmdbConfiguration getInstance() {
        return null;
    }

    String getApiKey();

    Tmdb getTmdb();

    String getBaseUrl();
}
