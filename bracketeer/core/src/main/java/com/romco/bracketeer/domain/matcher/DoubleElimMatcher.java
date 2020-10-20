package com.romco.bracketeer.domain.matcher;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.MatchResultEnum;
import com.romco.bracketeer.domain.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class DoubleElimMatcher implements Matcher {

    /*
    Double elim works the following way:
        - 1st Round - random seeding and matching
        - 2nd Round and forth
            - winners keep winner status (defined as 0 losses)
            - anyone who loses once drops to lower bracket
            - in losers' bracket,,
     */

    // TODO all of this
    @Override
    public Round generateRound(List<Participant> participants, SortMode mode, int numberOfRoundToGenerate) {
        List<Participant> winningBracket =
                participants.stream()
                            .filter(participant -> participant.getNumberOfMatchesWithResult(MatchResultEnum.LOSS) < 1)
                            .collect(Collectors.toList());
        List<Participant> losingBracket =
                participants.stream()
                            .filter(participant -> participant.getNumberOfMatchesWithResult(MatchResultEnum.LOSS) == 1)
                            .collect(Collectors.toList());


        return null;
    }

    @Override
    public int getMaxNumberOfRounds(int numberOfParticipants) {
        log.debug("in getMaxNumberOfRounds, numberOfParticipants: {}", numberOfParticipants);
        if (numberOfParticipants < 1) {
            log.warn("called getMaxNumberOfRounds with less than 1!");
            return 0;
        }
        double logOf2 = Math.log(numberOfParticipants) / Math.log(2);
        int roundedUpPlusOne = (int) Math.ceil(logOf2) + 1;
        log.info("numberOfParticipants: {}, sqrt: {}, roundedUpPlusOne: {}",
                 numberOfParticipants,
                 logOf2,
                 roundedUpPlusOne);
        return roundedUpPlusOne;
    }
}
