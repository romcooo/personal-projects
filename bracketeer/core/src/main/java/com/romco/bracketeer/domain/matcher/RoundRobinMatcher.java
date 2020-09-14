package com.romco.bracketeer.domain.matcher;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.Match;
import com.romco.bracketeer.domain.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RoundRobinMatcher implements Matcher {
    @Override
    public Round generateRound(List<Participant> participants, SortMode mode) {
        List<Participant> toPairList = mode.sort(participants);
        // first check if it's still possible - if one player played against everyone else
        boolean matchIsPossible = false;
        for (int i = 1; i < toPairList.size(); i++) {
            if (!toPairList.get(0).hasPlayedAgainst(participants.get(i))) {
                matchIsPossible = true;
            }
        }

        Round round = new Round();
        int matchCount = 1;

        // handle bye - need to update MatcherHelper for Round robin
        if (MatcherHelper.handleBye(toPairList, round, matchCount)) {
            matchCount++;
        }

        if (!matchIsPossible) {
            // TODO handle properly (or create an appropriate exception!)
            throw new RuntimeException("Everyone has already played with everyone!");
        }

        while (!toPairList.isEmpty()) {
            Participant current = toPairList.get(0);
            for (int i = 1; i < toPairList.size(); i++) {
                if (!current.hasPlayedAgainst(toPairList.get(i))) {
                    Participant next = toPairList.get(i);
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
                    break;
                }
                // if you reach the end and it didn't break, you didn't match anyone
                if (i == toPairList.size() - 1) {
                    throw new RuntimeException("Shouldn't get here");
                }
            }
        }

        return round;
    }
    
    @Override
    public int getMaxNumberOfRounds(int numberOfParticipants) {
        return numberOfParticipants - 1;
    }
}
