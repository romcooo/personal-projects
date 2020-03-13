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
    public Round generateRound(List<Participant> participants, MatcherMode mode) {
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
        
        // TODO
        return null;
    }
}
