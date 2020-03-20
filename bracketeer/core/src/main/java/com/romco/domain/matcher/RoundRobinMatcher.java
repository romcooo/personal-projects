package com.romco.domain.matcher;

import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Round;

import java.util.List;

public class RoundRobinMatcher implements Matcher {
    @Override
    public Round generateRound(List<Participant> participant, SortMode mode) {
        
        return null;
    }
    
    @Override
    public int getMaxNumberOfRounds(int numberOfParticipants) {
        return numberOfParticipants - 1;
    }
}
