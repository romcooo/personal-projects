package com.romco.bracketeer.model;

import com.romco.bracketeer.model.participant.Participant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Standings {
    List<Participant> participants;
    
    public Standings(List<Participant> participants) {
        this.participants = new ArrayList<>(participants);
        // sort by score descending
        this.participants.sort(Comparator.comparingDouble(Participant::getScore).reversed());
    }
    
    
    
}
