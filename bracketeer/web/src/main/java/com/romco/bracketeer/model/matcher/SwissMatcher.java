package com.romco.bracketeer.model.matcher;

import com.romco.bracketeer.model.tournament.Match;
import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

// package-private because TournamentFormat provides the preferred builder method
@Slf4j
class SwissMatcher implements Matcher {
    
    // package-private constructor
    SwissMatcher() {
    }



    @Override
    public Round generateRound(List<Participant> participants) {
        if (participants.isEmpty()) {
            log.info("Empty list passed, returning null");
            return null;
        }
        
        List<Participant> toPairList = new ArrayList<>(participants);

        if (participants.stream().allMatch((participant) -> participant.getScore() == 0)) {
            // if first round, shuffle
            Collections.shuffle(toPairList);
        } else {
            // sort (reversed because we want descending)
            log.debug("Before sorting: " + toPairList.toString());
            toPairList.sort(Comparator.comparingDouble(Participant::getScore).reversed());
            log.debug("After sorting: " + toPairList.toString());
        }
        
        Round round = new Round();
        
        // first need to check a bye
            if (toPairList.size() % 2 == 1) {
            int acceptableNumberOfByes = 0;
            for (int i = toPairList.size() - 1; i >= 0; i--) {
                Participant participant = toPairList.get(i);
                if (participant.getNumberOfByes() <= acceptableNumberOfByes) {
                    // create the bye match, then remove the participant
                    log.info("assigning bye to participant {}", participant);
                    Match match = new Match(participant);
    
                    participant.addPlayedMatch(match);
                    round.addMatch(match);
                    
                    toPairList.remove(i);
                    break;
                } else if (i == 0) {
                    acceptableNumberOfByes++;
                    i = toPairList.size() - 1;
                }

            }
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
                    break;
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
