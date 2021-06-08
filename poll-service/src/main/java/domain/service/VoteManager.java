package domain.service;

import domain.model.Count;
import domain.model.KeyGroupMovie;
import domain.model.RawSuggestion;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class VoteManager implements VoteManagerInterface {
    @SuppressWarnings("unused")
    @PersistenceContext(unitName = "PollPU")
    private EntityManager em;

    @Override
    public Count createPoll(@NotNull Count count) {
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
    public Count incrementYes(@NotNull int group_id, @NotNull int movie_id) {
        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        count.setNb_yes(count.getNb_yes() + 1);
        return count;
    }

    @Override
    public Count incrementNo(@NotNull int group_id, @NotNull int movie_id) {
        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        count.setNb_no(count.getNb_no() + 1);
        return count;
    }

    @Override
    public Count incrementMaybe(@NotNull int group_id, @NotNull int movie_id) {
        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        count.setNb_maybe(count.getNb_maybe() + 1);
        return count;
    }

    @Override
    public List<Count> getPolls(@NotNull int group_id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Count> criteria = builder.createQuery(Count.class);
        criteria.from(Count.class);
        List<Count> polls = em.createQuery(criteria).getResultList();
        polls.removeIf(count -> !Objects.equals(count.getGroup_id(), group_id));
        return polls;
    }

    @Override
    public List<Count> getResults(@NotNull int group_id) {
        return null;
    }  // get


    @Override
    public Count deletePoll(@NotNull int group_id, @NotNull int movie_id) {
        Count count = em.find(Count.class, new KeyGroupMovie(group_id, movie_id));
        if (count == null) {
            throw new EntityNotFoundException(String.format("Count with key (%d, %d) has not been found in table", group_id, movie_id));
        }
        em.remove(count);
        return count;
    }

    @Override
    public List<Count> deleteAllPolls(@NotNull int group_id) {
        return null;
    }

    @SuppressWarnings("SameParameterValue")
    private <T> Boolean checkIfExists(@NotNull Class<T> cls, @NotNull KeyGroupMovie key) {
        T entity = em.find(cls, key);
        return (entity != null);
    }
}
