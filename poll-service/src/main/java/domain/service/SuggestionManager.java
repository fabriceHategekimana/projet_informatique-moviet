package domain.service;

import domain.model.KeyGroupMovie;
import domain.model.Proposition;
import domain.model.RawSuggestion;
import domain.model.SuggestionWithCriteria;

import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApplicationScoped
public class SuggestionManager implements SuggestionManagerInterface {

    @SuppressWarnings("unused")
    @PersistenceContext(unitName = "PollPU")
    private EntityManager em;

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
    public RawSuggestion fetchRawSuggestion(@Nullable Integer group_id) throws NoResultException {
        Query query = em.createQuery("SELECT * FROM RawSuggestion ORDER BY added_at"); // limit 1?
        return (RawSuggestion) query.getSingleResult();
    }


    @Override
    @Transactional
    public RawSuggestion removeRawSuggestion(@NotNull int group_id, @NotNull int movie_id) throws EntityNotFoundException {
        RawSuggestion rawSuggestion = em.find(RawSuggestion.class, new KeyGroupMovie(group_id, movie_id));
        if (rawSuggestion == null) {
            throw new EntityNotFoundException(String.format("RawSuggestion with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(rawSuggestion);
        return rawSuggestion;
    }

    @Override
    @Transactional
    public int removeAllRawSuggestions(@NotNull int group_id) {
        Query query = em.createQuery("DELETE FROM RawSuggestion s WHERE s.group_id = :group_id");
        query.setParameter("group_id", group_id);

        return query.executeUpdate();
    }

    @Override
    public SuggestionWithCriteria computeSuggestionWithCriteria(@NotNull RawSuggestion rawSuggestion) {
        return SuggestionWithCriteria.fromRawSuggestion(rawSuggestion);
    }

    @Override
    @Transactional
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
    public SuggestionWithCriteria fetchSuggestionWithCriteria(@Nullable Integer group_id) throws NoResultException {
        Query query = em.createQuery("SELECT * FROM SuggestionWithCriteria ORDER BY added_at");  // limit 1?
        return (SuggestionWithCriteria) query.getSingleResult();
    }


    @Override
    @Transactional
    public SuggestionWithCriteria removeSuggestionWithCriteria(@NotNull int group_id, @NotNull int movie_id) throws EntityNotFoundException {
        SuggestionWithCriteria suggestionWithCriteria = em.find(SuggestionWithCriteria.class, new KeyGroupMovie(group_id, movie_id));
        if (suggestionWithCriteria == null) {
            throw new EntityNotFoundException(String.format("SuggestionWithCriteria with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(suggestionWithCriteria);
        return suggestionWithCriteria;
    }

    @Override
    @Transactional
    public int removeAllSuggestionsWithCriteria(@NotNull int group_id) {
        Query query = em.createQuery("DELETE FROM SuggestionWithCriteria s WHERE s.group_id = :group_id");
        query.setParameter("group_id", group_id);

        return query.executeUpdate();
    }

    @Override
    public Proposition computeProposition(@NotNull SuggestionWithCriteria suggestionWithCriteria) {
        return Proposition.fromSuggestionWithCriteria(suggestionWithCriteria);
    }

    @Override
    @Transactional
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
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Proposition> criteria = builder.createQuery(Proposition.class);
        Root<Proposition> root = criteria.from(Proposition.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("group_id"), group_id));

        return em.createQuery(criteria).getResultList();
    }

    @Override
    @Transactional
    public Proposition removeProposition(@NotNull int group_id, @NotNull int movie_id) throws EntityNotFoundException {
        Proposition proposition = em.find(Proposition.class, new KeyGroupMovie(group_id, movie_id));
        if (proposition == null) {
            throw new EntityNotFoundException(String.format("Proposition with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(proposition);
        return proposition;
    }

    @Override
    @Transactional
    public int removeAllPropositions(@NotNull int group_id) {
        Query query = em.createQuery("DELETE FROM Proposition p WHERE p.group_id = :group_id");
        query.setParameter("group_id", group_id);

        return query.executeUpdate();
    }

    private <T> Boolean checkIfExists(@NotNull Class<T> cls, @NotNull KeyGroupMovie key) {
        T entity = em.find(cls, key);
        return (entity != null);
    }
}
