package domain.service;

import domain.model.KeyGroupMovie;
import domain.model.Proposition;
import domain.model.RawSuggestion;
import domain.model.SuggestionWithCriteria;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApplicationScoped
public class SuggestionManager implements SuggestionManagerInterface {

    @SuppressWarnings("unused")
    @PersistenceContext(unitName = "PollPU")
    private EntityManager em;

    @Setter
    @Getter
    private int defaultQuantity = 10;

    @Override
    @Transactional
    public RawSuggestion addRawSuggestion(@NotNull RawSuggestion rawSuggestion) throws EntityExistsException, IllegalArgumentException {
        int group_id = rawSuggestion.getGroup_id();
        int movie_id = rawSuggestion.getMovie_id();

        rawSuggestion.checkValidity();
        if (checkIfExists(RawSuggestion.class, new KeyGroupMovie(group_id, movie_id))) {
            throw new EntityExistsException(String.format("RawSuggestion with similar key (%d, %d) has already been found in table", group_id, movie_id));
        }

        em.persist(rawSuggestion);
        return rawSuggestion;
    }

    @Override
    public RawSuggestion fetchRawSuggestion(@Nullable Integer group_id) {
        return null;
    }

    @Override
    public List<RawSuggestion> fetchRawSuggestions(@Nullable Integer group_id) {
        return fetchRawSuggestions(group_id, defaultQuantity);
    }

    @Override
    public List<RawSuggestion> fetchRawSuggestions(@Nullable Integer group_id, int quantity) {
        return null;
    }

    @Override
    public RawSuggestion removeRawSuggestion(@NotNull int group_id, @NotNull int movie_id) throws EntityNotFoundException {
        RawSuggestion rawSuggestion = em.find(RawSuggestion.class, new KeyGroupMovie(group_id, movie_id));
        if (rawSuggestion == null) {
            throw new EntityNotFoundException(String.format("RawSuggestion with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(rawSuggestion);
        return rawSuggestion;
    }

    @Override
    public List<RawSuggestion> removeAllRawSuggestions(@NotNull int group_id) {
        return null;
    }

    @Override
    public SuggestionWithCriteria computeSuggestionWithCriteria(@NotNull RawSuggestion rawSuggestion) {
        // Need Communication with Group-Service and Movie-Service via API
        // Will be managed with GroupServiceRequester and MovieServiceRequester
        return null;
    }

    @Override
    public SuggestionWithCriteria addSuggestionWithCriteria(@NotNull SuggestionWithCriteria suggestionWithCriteria) throws EntityExistsException, IllegalArgumentException {
        int group_id = suggestionWithCriteria.getGroup_id();
        int movie_id = suggestionWithCriteria.getMovie_id();

        suggestionWithCriteria.checkValidity();
        if (checkIfExists(SuggestionWithCriteria.class, new KeyGroupMovie(group_id, movie_id))) {
            throw new EntityExistsException(String.format("SuggestionWithCriteria with similar key (%d, %d) has already been found in table", group_id, movie_id));
        }

        em.persist(suggestionWithCriteria);
        return suggestionWithCriteria;
    }


    @Override
    public SuggestionWithCriteria fetchSuggestionWithCriteria(@Nullable Integer group_id) {
        return null;
    }

    @Override
    public List<SuggestionWithCriteria> fetchSuggestionsWithCriteria(@Nullable Integer group_id) {
        return fetchSuggestionsWithCriteria(group_id, defaultQuantity);
    }

    @Override
    public List<SuggestionWithCriteria> fetchSuggestionsWithCriteria(@Nullable Integer group_id, int quantity) {
        return null;
    }

    @Override
    public SuggestionWithCriteria removeSuggestionWithCriteria(@NotNull int group_id, @NotNull int movie_id) throws EntityNotFoundException {
        SuggestionWithCriteria suggestionWithCriteria = em.find(SuggestionWithCriteria.class, new KeyGroupMovie(group_id, movie_id));
        if (suggestionWithCriteria == null) {
            throw new EntityNotFoundException(String.format("SuggestionWithCriteria with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(suggestionWithCriteria);
        return suggestionWithCriteria;
    }

    @Override
    public List<SuggestionWithCriteria> removeAllSuggestionsWithCriteria(@NotNull int group_id) {
        return null;
    }

    @Override
    public Proposition computeProposition(@NotNull SuggestionWithCriteria suggestionWithCriteria) {
        return null;
    }

    @Override
    public Proposition addProposition(@NotNull Proposition proposition) throws EntityExistsException, IllegalArgumentException {
        int group_id = proposition.getGroup_id();
        int movie_id = proposition.getMovie_id();

        proposition.checkValidity();
        if (checkIfExists(Proposition.class, new KeyGroupMovie(group_id, movie_id))) {
            throw new EntityExistsException(String.format("Proposition with similar key (%d, %d) has already been found in table", group_id, movie_id));
        }

        em.persist(proposition);
        return proposition;
    }

    @Override
    public List<Proposition> getPropositions(@NotNull int group_id) {
        return null;
    }

    @Override
    public Proposition removeProposition(@NotNull int group_id, @NotNull int movie_id) throws EntityNotFoundException {
        Proposition proposition = em.find(Proposition.class, new KeyGroupMovie(group_id, movie_id));
        if (proposition == null) {
            throw new EntityNotFoundException(String.format("Proposition with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(proposition);
        return proposition;
    }

    @Override
    public List<Proposition> removeAllPropositions(@NotNull int group_id) {
        return null;
    }

    private <T> Boolean checkIfExists(@NotNull Class<T> cls, @NotNull KeyGroupMovie key) {
        T entity = em.find(cls, key);
        return (entity != null);
    }
}
