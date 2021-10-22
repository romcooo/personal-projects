package com.romco.bracketeer.domain.participant;

import java.util.List;

public class Team<T extends Participant> extends Participant {
    private List<T> members;
    
    public boolean add(T member) {
        if (!members.contains(member)) {
            members.add(member);
            return true;
        } else {
            return false;
        }
    }
}
