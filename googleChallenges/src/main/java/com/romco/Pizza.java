package com.romco;

import lombok.Data;

class Pizza {
    private int slices;
    private int type;
    
    public Pizza(int slices, int type) {
        this.slices = slices;
        this.type = type;
    }
    
    public int getSlices() {
        return slices;
    }

    public int getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "" + slices;
    }
}

