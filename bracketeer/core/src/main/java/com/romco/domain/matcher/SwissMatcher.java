package com.romco.domain.matcher;

import com.romco.domain.tournament.Match;
import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// package-private because TournamentFormat provides the preferred builder method
@Slf4j
public
class SwissMatcher implements Matcher {
    
    // package-private constructor
    public SwissMatcher() {
    }

    @Override
    public Round generateRound(List<Participant> participants, SortMode mode) {
        if (participants.isEmpty()) {
            log.info("Empty list passed, returning null");
            return null;
        }
        
//        List<Participant> toPairList = new ArrayList<>(participants);
        List<Participant> toPairList = mode.sort(participants);
        
//        if (participants.stream().allMatch((participant) -> participant.getScore() == 0)) {
//            // if first round, shuffle
//            Collections.shuffle(toPairList);
//        } else {
//            // sort (reversed because we want descending)
//            log.debug("Before sorting: " + toPairList.toString());
//            toPairList.sort(Comparator.comparingDouble(Participant::getScore).reversed());
//            log.debug("After sorting: " + toPairList.toString());
//        }

        log.debug("toPairList after sorting: {}", toPairList);
        Round round = new Round();

        int matchCount = 1;
        // first need to check a bye
//        if (toPairList.size() % 2 == 1) {
//            Match byeMatch = MatcherHelper.handleBye(toPairList, round, matchCount);
//
//            byeMatch.setMatchNumber(matchCount);
//            byeMatch.setOfRound(round);
//            round.addMatch(byeMatch);
//            matchCount++;
//        }
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
            
            while (true) {
                // if there is noone to pair with, it means that everyone has already played with everyone
                /* TODO this could be solved by simply setting everyone's hasPlayed list to empty as if noone
                 * played with noone
                 */
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
                if (current.hasPlayedAgainst(next)) {
                    // in this case, keep going
                    i2++;
                } else {
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
                    break;
                }
            }
        }
    
        log.info("Matches: {}", round.getMatches().toString());
        return round;
    }
}
