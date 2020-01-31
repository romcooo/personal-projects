package com.romco.pizzaChallenge;

import lombok.Data;

public class Pizza {
    private int slices;
    private short type;
    
    public Pizza(int slices, short type) {
        this.slices = slices;
        this.type = type;
    }
    
    public int getSlices() {
        return slices;
    }

    public short getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "" + slices;
    }
}

