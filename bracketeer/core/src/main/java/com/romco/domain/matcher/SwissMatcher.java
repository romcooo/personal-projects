package com.romco.domain.matcher;

import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Match;
import com.romco.domain.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// package-private because TournamentFormat provides the preferred builder method
@Slf4j
public
class SwissMatcher implements Matcher {
    
    @Override
    public Round generateRound(List<Participant> participants, SortMode mode) {
        if (participants.isEmpty()) {
            log.info("Empty list passed, returning null");
            return null;
        }
        
//        List<Participant> toPairList = new ArrayList<>(participants);
        List<Participant> toPairList = mode.sort(participants);

        log.debug("toPairList after sorting: {}", toPairList);
        Round round = new Round();

        int matchNumber = 1;
        if (MatcherHelper.handleBye(toPairList,
                                    round,
                                    matchNumber)) {
            matchNumber++;
        }
        
        // go through list as long as there is someone to pair
        while (!toPairList.isEmpty()) {
            // get the participant with most points
            Participant current = toPairList.get(0);
            log.debug("current: {}", current);
    
            // get the participant with which the current has not yet played and which has the highest score
            Optional<Participant> opponentOptional = toPairList.stream()
                                             .filter(p -> !current.hasPlayedAgainst(p))
                                             .filter(p -> !p.equals(current))
                                             .max(Comparator.comparingDouble(Participant::getScore));
    
            if (opponentOptional.isEmpty()) {
                throw new RuntimeException("everyone already played with everyone in a swiss tournament");
            }
            
            Participant opponent = opponentOptional.get();
            log.debug("participant {} has not yet played against: {}", current, opponent);
    
            // this is the goal - match current with next, then remove both from the list and keep going
            log.info("matching {} with {}", current, opponent);
    
            Match match = new Match(current, opponent);
            match.setMatchNumber(matchNumber);
            match.setOfRound(round);
            matchNumber++;
    
            log.debug(match.toString());
            round.addMatch(match);
    
            current.addPlayedMatch(match);
            opponent.addPlayedMatch(match);
    
            current.setPlayedAgainstBiDirectional(opponent);
    
            toPairList.remove(current);
            toPairList.remove(opponent);
        }
    
        log.info("Matches: {}", round.getMatches().toString());
        return round;
    }
    
    @Override
    public int getMaxNumberOfRounds(int numberOfParticipants) {
        return numberOfParticipants - 1;
    }
}
