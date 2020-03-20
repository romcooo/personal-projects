package com.romco.domain.matcher;

import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Match;
import com.romco.domain.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SingleElimMatcher implements Matcher {
    @Override
    public Round generateRound(List<Participant> participants, SortMode mode) {
        List<Participant> toPairList = mode.sort(participants);
        double highestScore = toPairList.get(0).getScore();
        toPairList = toPairList.stream()
                               .filter(p -> p.getScore() == highestScore)
                               .collect(Collectors.toList());
        log.debug("In generateRound, after elimination: {}", toPairList);

        Round round = new Round();
        int matchCount = 1;

        // first need to check a bye
        if (MatcherHelper.handleBye(toPairList,
                                    round,
                                    matchCount)) {
            matchCount++;
        }

        // go through list as long as there is someone to pair
        while (!toPairList.isEmpty()) {
            // get the participant with most points
            Participant current = toPairList.get(0);
            log.debug("current: {}", current);
            // then keep going through the rest of the list starting with the 2nd most point participant
            int i2 = 1;
            log.debug("at {} with size {}", i2, toPairList.size());
            if (i2 == toPairList.size()) {
                log.error("Error - either everyone has already played with everyone or some kind of " +
                                  "logical fault has happened." +
                                  "\nTrying to pair participant: {} " +
                                  "\n withParticipants: {}", current, toPairList);
                // TODO fix an error where if this happens it keeps looping forever
                throw new RuntimeException("everyone already played with everyone in a swiss tournament");
//                    break;
            }
            Participant next = toPairList.get(i2);
            log.debug("next: {}", next);
            // this is the goal - match current with next, then remove both from the list and keep going
            log.info("matching {} with {}", current, next);

            Match match = new Match(current, next);
            match.setMatchNumber(matchCount);
            match.setOfRound(round);
            matchCount++;

            log.debug(match.toString());
            round.addMatch(match);

            current.addPlayedMatch(match);
            next.addPlayedMatch(match);

            current.setPlayedAgainstBiDirectional(next);

            toPairList.remove(current);
            toPairList.remove(next);
        }
        return round;
    }

    // TODO this could produce an entire bracket
}
