package com.romco.bracketeer.model.matchers;

import com.romco.bracketeer.model.Match;
import com.romco.bracketeer.model.Participant;
import com.romco.bracketeer.model.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class SwissMatcher implements Matcher {
    @Override
    public Round generateRound(List<Participant> participants) {
        if (participants.isEmpty()) {
            log.info("Empty list passed, returning null");
            return null;
        }
        
        List<Participant> toPairList = new ArrayList<>(participants);
        
        // sort (reversed because we want descending)
        toPairList.sort(Comparator.comparingDouble(Participant::getScore).reversed());
        List<Match> matches = new ArrayList<>();
        
        // first need to check a bye
        if (toPairList.size() % 2 == 1) {
            for (int i = toPairList.size() - 1; i >= 0; i--) {
                Participant participant = toPairList.get(i);
                if (!participant.hadABye()) {
                    // create the bye match, then remove the participant
                    matches.add(new Match(participant));
                    toPairList.remove(i);
                    break;
                }
            }
        }
        
        // go through list as long as there is someone to pair
        while (!toPairList.isEmpty()) {
            // get the participant with most points
            Participant current = toPairList.get(0);
            log.trace("current: {}", current);
            // then keep going through the rest of the list starting with the 2nd most point participant
            int i2 = 1;
            
            while (true) {
                // if there is noone to pair with, it means that everyone has already played with everyone
                /* TODO this could be solved by simply setting everyone's hasPlayed list to empty as if noone
                 * played with noone
                 */
                log.trace("at {} with size {}", i2, toPairList.size());
                if (i2 == toPairList.size()) {
                    log.error("Error - either everyone has already played with everyone or some kind of " +
                                      "logical fault has happened." +
                                      "\nTrying to pair participant: {} " +
                                      "\n withParticipants: {}", current, toPairList);
                    break;
                }
                Participant next = toPairList.get(i2);
                log.trace("next: {}", next);
                if (current.hasPlayedAgainst(next)) {
                    // in this case, keep going
                    i2++;
                } else {
                    // this is the goal - match current with next, then remove both from the list and keep going
                    log.info("matching {} with {}", current, next);
                    matches.add(new Match(current, next));
                    toPairList.remove(current);
                    toPairList.remove(next);
                    break;
                }
            }
        }
    
        log.info("Matches: {}", matches.toString());
        return new Round(matches);
    }
}
