package com.romco.bracketeer.domain.matcher;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.Match;
import com.romco.bracketeer.domain.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MatcherHelper {

    private MatcherHelper() {
    }

    public static boolean handleBye(List<Participant> toHandle,
                                    Round currentRound,
                                    int matchCount) {
        if (toHandle.size() % 2 == 1) {
            int acceptableNumberOfByes = 0;
            for (int i = toHandle.size() - 1; i >= 0; i--) {
                Participant participant = toHandle.get(i);
                if (participant.getNumberOfByes() <= acceptableNumberOfByes) {
                    // create the bye match, then remove the participant
                    log.info("assigning bye to participant {}", participant);

                    Match match = new Match(participant);
                    match.setMatchNumber(matchCount);
                    match.setOfRound(currentRound);
                    
                    currentRound.addMatch(match);
                    participant.addPlayedMatch(match);
    
                    toHandle.remove(i);
                    
                    return true;
                } else if (i == 0) {
                    acceptableNumberOfByes++;
                    i = toHandle.size() - 1;
                }
            }
            log.error("Can't handle bye for list: {}", toHandle);
        }
        return false;
    }
}
