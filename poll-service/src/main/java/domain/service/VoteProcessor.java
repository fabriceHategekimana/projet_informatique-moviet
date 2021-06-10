package domain.service;

import domain.model.Proposition;
import domain.model.RawSuggestion;
import domain.model.SuggestionWithCriteria;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VoteProcessor implements Runnable {
    // call compute methods defined in VoteManager
    // alternate between tables to compute new suggestions/propositions
    // (when 'Propositions' empty for certain group get from 'Processed', when 'Processed' empty get from 'Unprocessed')

    private static final Logger LOGGER = Logger.getLogger(VoteProcessor.class.getName());

    private final int group_id;
    private final VoteManager voteManager = new VoteManager();
    private final SuggestionManager suggestionManager = new SuggestionManager();

    public VoteProcessor(int group_id) {
        this.group_id = group_id;
    }

    public void run() {
        try {
            boolean wait = true;
            while (voteManager.isVoting(group_id)) {  // add HaveThreBeenAnUpdate to reduce sql calls
                try {
                    RawSuggestion rawSuggestion = suggestionManager.fetchRawSuggestion(group_id);
                    if (rawSuggestion != null) {
                        wait = false;
                        SuggestionWithCriteria suggestionWithCriteria = SuggestionWithCriteria.fromRawSuggestion(rawSuggestion);
                        try {
                            suggestionManager.addSuggestionWithCriteria(suggestionWithCriteria);
                            suggestionManager.removeRawSuggestion(rawSuggestion.getGroup_id(), rawSuggestion.getMovie_id());
                        } catch (Exception ignore) {
                        }
                    }
                } catch (Exception ignore) {
                }

                if (!voteManager.isVoting(group_id)) {
                    break;
                }

                try {
                    SuggestionWithCriteria suggestionWithCriteria = suggestionManager.fetchSuggestionWithCriteria(group_id);
                    if (suggestionWithCriteria != null) {
                        wait = false;
                        Proposition proposition = Proposition.fromSuggestionWithCriteria(suggestionWithCriteria);
                        try {
                            suggestionManager.addProposition(proposition);
                            suggestionManager.removeSuggestionWithCriteria(suggestionWithCriteria.getGroup_id(), suggestionWithCriteria.getMovie_id());
                        } catch (Exception ignore) {
                        }
                    }
                } catch (Exception ignore) {
                }

                if (wait) {
                    Thread.sleep(10000);
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Thread (%d) has caught an exception", group_id));
        }
    }

}
