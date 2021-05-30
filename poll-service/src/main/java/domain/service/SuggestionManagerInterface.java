package domain.service;

import domain.model.Proposition;
import domain.model.RawSuggestion;
import domain.model.SuggestionWithCriteria;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;  // javax.annotation.NotNull work...
import java.util.List;

public interface SuggestionManagerInterface {
    RawSuggestion addRawSuggestion(@NotNull RawSuggestion rawSuggestion);
    RawSuggestion fetchRawSuggestion(@Nullable Integer group_id);  // group_id null => we check timestamp of all groups
    List<RawSuggestion> fetchRawSuggestions(@Nullable Integer group_id);
    List<RawSuggestion> fetchRawSuggestions(@Nullable Integer group_id, int quantity);
    RawSuggestion removeRawSuggestion(@NotNull int group_id, @NotNull int movie_id);
    List<RawSuggestion> removeAllRawSuggestions(@NotNull int group_id);

    SuggestionWithCriteria computeSuggestionWithCriteria(@NotNull RawSuggestion rawSuggestion);
    SuggestionWithCriteria addSuggestionWithCriteria(@NotNull SuggestionWithCriteria suggestionWithCriteria);
    SuggestionWithCriteria fetchSuggestionWithCriteria(@Nullable Integer group_id);
    List<SuggestionWithCriteria> fetchSuggestionsWithCriteria(@Nullable Integer group_id);
    List<SuggestionWithCriteria> fetchSuggestionsWithCriteria(@Nullable Integer group_id, int quantity);
    SuggestionWithCriteria removeSuggestionWithCriteria(@NotNull int group_id, @NotNull int movie_id);
    List<SuggestionWithCriteria> removeAllSuggestionsWithCriteria(@NotNull int group_id);

    Proposition computeProposition(@NotNull SuggestionWithCriteria suggestionWithCriteria);
    Proposition addProposition(@NotNull Proposition proposition);
    Proposition removeProposition(@NotNull int group_id, @NotNull int movie_id);
    List<Proposition> getPropositions(@NotNull int group_id);
    List<Proposition> removeAllPropositions(@NotNull int group_id);

}