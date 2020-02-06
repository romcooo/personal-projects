package com.romco.bracketeer.model;

import java.util.List;

public interface Tournament<T extends Participant> {
    boolean addParticipant(T participant);
    boolean removeParticipant(T participant);
    
    Round generateNextRound();
    Round getRound(int n);
    
    
    
    List<Match> getMatchesForParticipant(Participant participant);
    
}
