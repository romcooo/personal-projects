package com.romco.bracketeer.model;

public class Player extends Participant {
    private final int id;
    
    public Player(int id) {
        super();
        this.id = id;
    }
    
    public Player(int id, String name) {
        super();
        this.id = id;
        super.name = name;
    }
}
