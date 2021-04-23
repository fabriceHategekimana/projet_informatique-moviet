# TMDb Java wrapper - Cheat sheet

## Git Repo
https://github.com/UweTrottmann/tmdb-java/

## General Structure :
* entities: Data structure to manipulate data and effectuate calls
* services: Methods, API Requests
* enumerations: Complementary data structures (passed as argument etc)


https://developers.themoviedb.org/3/authentication/how-do-i-generate-a-session-id
* Session Id / Guest id for rating


## Useful methods

MoviesService :
* summary : Movie Basic Info from id
* credits : Cast, crew
* images : Posters Backdrops
* keywords : plot keywords
* similar : similar movies
* recommendations : recommendations from id
* releaseDates : release dates, certifications
* reviews : reviews
* videos : trailers, teasers, clips, etc...
* popular : current popular movies on TMDb
* topRated : top rated movies on TMDb

* upcoming : list of upcoming movies in theatres
* nowPlaying : list of movies in theatres. (optional) specify a region parameter

GenresService :

* movie : Get the list of movie genres
* movies : Get a list of movies by Genre id.

ConfigurationService :
* Get the system wide configuration information for images. Some elements of the API require.
    * An image is gotten through : base_url + ..._sizes + file_path
    * base_url and {logo, poster, profile, still}_sizes are gotten through this request


DiscoverService :
* discoverMovie : Discover movies by different types of data

ListsService :
* Manage a list of movies

SearchService :
* Query for movies, keyword, users...

AccountService :
* watchlistMovies : Get a list of all the movies you have added to your watchlist

AuthenticationService :
* requestToken : Requests authentication Token
* validateToken : Attempts to Login with a Request Token and Username/Password.
