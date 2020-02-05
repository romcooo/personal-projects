package com.romco.bracketeer.model;

public interface Tournament<T extends Participant> {
    boolean addParticipant(T participant);
    boolean removeParticipant(T participant);
    
}
