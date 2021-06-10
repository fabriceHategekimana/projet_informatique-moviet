package domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroupPreferences {
    public List<Set<Integer>> keywordsList;
    public List<Set<Integer>> genresList;
    public List<YearBound> yearBoundList;

    public GroupPreferences(List<Set<Integer>> genresList, List<Set<Integer>> keywordsList, List<YearBound> yearBoundList) {
        this.genresList = genresList;
        this.keywordsList = keywordsList;
        this.yearBoundList = yearBoundList;
    }

    public static GroupPreferences fromMoviePreferences(List<MoviePreferences> moviePreferencesList) {
        List<Set<Integer>> genresList = new ArrayList<>();
        List<Set<Integer>> keywordsList = new ArrayList<>();
        List<YearBound> yearBoundList = new ArrayList<>();

        for (MoviePreferences moviePreferences : moviePreferencesList) {
            genresList.add(moviePreferences.getGenres_id());
            keywordsList.add(moviePreferences.getKeywords_id());
            yearBoundList.add(new YearBound(moviePreferences.getYear_from(), moviePreferences.getYear_to()));
        }

        return new GroupPreferences(genresList, keywordsList, yearBoundList);
    }

    public static class YearBound {
        public Integer year_from;
        public Integer year_to;

        public YearBound(Integer year_from, Integer year_to) {
            this.year_from = year_from;
            this.year_to = year_to;
        }
    }
}
