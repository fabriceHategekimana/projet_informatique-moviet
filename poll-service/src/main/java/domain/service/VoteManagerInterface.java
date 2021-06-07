package domain.service;

import domain.model.Count;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface VoteManagerInterface {
    Count createPoll(@NotNull Count count);

    Count incrementYes(@NotNull int group_id, @NotNull int movie_id);
    Count incrementNo(@NotNull int group_id, @NotNull int movie_id);
    Count incrementMaybe(@NotNull int group_id, @NotNull int movie_id);

    List<Count> getPolls(@NotNull int group_id);
    List<Count> getResults(@NotNull int group_id);

    Count deletePoll(@NotNull int group_id, @NotNull int movie_id);
    List<Count> deletePolls(@NotNull int group_id);
}
