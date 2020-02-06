package com.romco.bracketeer.model;

import com.romco.bracketeer.model.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Match {
    int id;
    private Participant participant1, participant2;
    private int bestOf;
    private int participant1Score, participant2Score;
    private boolean isBye;
    
    public Match(Participant participant1) {
        this.participant1 = participant1;
        this.isBye = true;
    }
    
    public Match(Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }
    
}
