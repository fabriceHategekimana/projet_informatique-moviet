/* *
 * Interface of a movie
 * 
 * */
export interface Movie {
    id: number;
    title: string;
    release_year: number;
    score: number;
    poster_url: string;
    backdrop_url: string;
    genres: string[];
  }