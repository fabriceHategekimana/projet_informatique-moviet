/* *
 * Interface for the movie preferences
 * 
 * */
export interface MoviePreferences {
    keywordsId: number[],
    genresId:   number[],
    yearFrom:   number | null,
    yearTo:   number | null,
}