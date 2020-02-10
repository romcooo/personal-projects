package com.romco.bracketeer.model.participant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Player extends Participant {
    
    public Player(String name) {
        super();
        super.name = name;
    }
    
    public Player(int id, String name) {
        super();
        super.id = id;
        super.name = name;
    }
}
