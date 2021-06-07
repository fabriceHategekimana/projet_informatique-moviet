/* *
 * Interface of a keyword
 * 
 * */
export interface Keyword {
    id: number | null,
    name: string
}

export interface KeywordResults {
    id: number,
    page: number,
    total_pages: number,
    total_results: number,
    results: Keyword[]
}