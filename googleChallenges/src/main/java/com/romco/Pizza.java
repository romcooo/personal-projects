package com.romco;

import lombok.Data;

@Data
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
    
    public void setType(int type) {
        this.type = type;
    }
    
    public void setSlices(int slices) {
        this.slices = slices;
    }
    
    @Override
    public String toString() {
        return "" + slices;
    }
}

