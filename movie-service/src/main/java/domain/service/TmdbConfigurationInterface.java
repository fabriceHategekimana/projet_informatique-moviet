package domain.service;

import com.uwetrottmann.tmdb2.Tmdb;

public interface TmdbConfigurationInterface {

    Tmdb getTmdb();

    String getBaseUrl();
}
