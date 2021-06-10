package domain.service;

import domain.model.Count;
import domain.model.KeyGroupMovie;
import domain.model.RawSuggestion;
import domain.model.Voting;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VoteManager implements VoteManagerInterface {
    @SuppressWarnings("unused")
    @PersistenceContext(unitName = "PollPU")
    private EntityManager em;

    @Override
    @Transactional
    public Voting beginVoting(@NotNull Voting voting) throws EntityExistsException, IllegalArgumentException {
        int group_id = voting.getGroup_id();

        voting.checkValidity();
        if (em.find(Voting.class, group_id) != null) {
            throw new EntityExistsException(String.format("Group '%d' has already been found in table", group_id));
        }

        em.persist(voting);
        return voting;
    }

    @Transactional
    private Voting removeVoting(@NotNull int group_id) throws EntityNotFoundException, IllegalArgumentException {
        Voting voting = em.find(Voting.class, group_id);
        if (voting == null) {
            throw new EntityNotFoundException(String.format("Group with key %d has not been found in table", group_id));
        }
        em.remove(voting);
        return voting;
    }

    @Override
    public Voting endVoting(@NotNull int group_id) throws EntityNotFoundException, IllegalArgumentException {
        Voting voting = removeVoting(group_id);

        SuggestionManager suggestionManager = new SuggestionManager();
        try {
            suggestionManager.removeAllRawSuggestions(group_id);
        } catch (EntityNotFoundException ignore) {
        }
        try {
            suggestionManager.removeAllSuggestionsWithCriteria(group_id);
        } catch (EntityNotFoundException ignore) {
        }
        try {
            suggestionManager.removeAllPropositions(group_id);
        } catch (EntityNotFoundException ignore) {
        }

        return voting;
    }

    @Override
    @Transactional
    public Count createPoll(@NotNull Count count) throws EntityExistsException, IllegalArgumentException {
        int group_id = count.getGroup_id();
        int movie_id = count.getMovie_id();

        count.checkValidity();
        if (checkIfExists(RawSuggestion.class, new KeyGroupMovie(group_id, movie_id))) {
            throw new EntityExistsException(String.format("Count with similar key (%d, %d) has already been found in table", group_id, movie_id));
        }

        em.persist(count);
        return count;
    }

    @Override
    @Transactional
    public Count incrementYes(@NotNull int group_id, @NotNull int movie_id) throws EntityNotFoundException, IllegalArgumentException {
        new Count(group_id, movie_id).checkValidity();

        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        if (count == null) {
            throw new EntityNotFoundException(String.format("Count with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        count.setNb_yes(count.getNb_yes() + 1);
        return count;
    }

    @Override
    @Transactional
    public Count incrementNo(@NotNull int group_id, @NotNull int movie_id) {
        new Count(group_id, movie_id).checkValidity();

        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        if (count == null) {
            throw new EntityNotFoundException(String.format("Count with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        count.setNb_no(count.getNb_no() + 1);
        return count;
    }

    @Override
    @Transactional
    public Count incrementMaybe(@NotNull int group_id, @NotNull int movie_id) {
        new Count(group_id, movie_id).checkValidity();

        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        if (count == null) {
            throw new EntityNotFoundException(String.format("Count with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        count.setNb_maybe(count.getNb_maybe() + 1);
        return count;
    }

    @Override
    public List<Count> getPolls(@NotNull int group_id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Count> criteria = builder.createQuery(Count.class);
        Root<Count> root = criteria.from(Count.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("group_id"), group_id));

        return em.createQuery(criteria).getResultList();
    }

    @Override
    public List<Count> getResults(@NotNull int group_id) {
        List<Count> counts = getPolls(group_id);

        float maxScore = Collections.max(
                counts.stream()
                        .map(Count::computeScore)
                        .collect(Collectors.toList()));

        // We take only movies with score == maxScore,
        // alternatively we could return the whole list but sorted
        return counts.stream().filter(count -> count.computeScore() == maxScore).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Count deletePoll(@NotNull int group_id, @NotNull int movie_id) {
        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        if (count == null) {
            throw new EntityNotFoundException(String.format("Count with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(count);
        return count;
    }

    @Override
    @Transactional
    public int deleteAllPolls(@NotNull int group_id) {

        Query query = em.createQuery("DELETE FROM Count c WHERE c.group_id = :group_id");
        query.setParameter("group_id", group_id);

        return query.executeUpdate();
    }

    @SuppressWarnings("SameParameterValue")
    private <T> Boolean checkIfExists(@NotNull Class<T> cls, @NotNull KeyGroupMovie key) {
        T entity = em.find(cls, key);
        return (entity != null);
    }
}
