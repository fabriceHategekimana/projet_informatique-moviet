package domain.service;

import domain.model.Count;
import domain.model.Voting;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface VoteManagerInterface {

    Voting beginVoting(@NotNull Voting voting);

    Voting endVoting(@NotNull int group_id);

    Count createPoll(@NotNull Count count);

    Count incrementYes(@NotNull int group_id, @NotNull int movie_id);

    Count incrementNo(@NotNull int group_id, @NotNull int movie_id);

    Count incrementMaybe(@NotNull int group_id, @NotNull int movie_id);

    List<Count> getPolls(@NotNull int group_id);

    List<Count> getResults(@NotNull int group_id);  // with maximum score (can be a draw)

    Count deletePoll(@NotNull int group_id, @NotNull int movie_id);

    int deleteAllPolls(@NotNull int group_id);
}
