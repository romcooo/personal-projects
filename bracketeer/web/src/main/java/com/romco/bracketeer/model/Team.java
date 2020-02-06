package com.romco.bracketeer.model;

import java.util.List;

public class Team<T extends Participant> extends Participant {
    private List<T> members;
    
    public Team() {
    }
    
    public boolean add(T member) {
        if (!members.contains(member)) {
            members.add(member);
            return true;
        } else {
            return false;
        }
    }
}
