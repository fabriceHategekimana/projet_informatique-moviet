package domain.service;

import domain.model.Count;

import javax.validation.constraints.NotNull;
import java.util.List;

public class VoteManager implements VoteManagerInterface{
    @Override
    public Count createPoll(@NotNull int group_id, @NotNull int movie_id) {
        return null;
    }

    @Override
    public Count incrementYes(@NotNull int group_id, @NotNull int movie_id) {
        return null;
    }

    @Override
    public Count incrementNo(@NotNull int group_id, @NotNull int movie_id) {
        return null;
    }

    @Override
    public Count incrementMaybe(@NotNull int group_id, @NotNull int movie_id) {
        return null;
    }

    @Override
    public List<Count> getPolls(@NotNull int group_id) {
        return null;
    }

    @Override
    public List<Count> getResults(@NotNull int group_id) {
        return null;
    }

    @Override
    public Count deletePoll(@NotNull int group_id, @NotNull int movie_id) {
        return null;
    }

    @Override
    public List<Count> deletePolls(@NotNull int group_id) {
        return null;
    }
}
