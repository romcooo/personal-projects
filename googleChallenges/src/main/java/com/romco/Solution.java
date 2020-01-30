package com.romco;

import java.util.List;

public class Solution {
    private List<Pizza> orderedPizzas;
    
    public Solution(List<Pizza> orderedPizzas) {
        this.orderedPizzas = List.copyOf(orderedPizzas);
    }


    public List<Pizza> getOrderedPizzas() {
        return orderedPizzas;
    }
    
    public void setOrderedPizzas(List<Pizza> orderedPizzas) {
        this.orderedPizzas = orderedPizzas;
    }
    
    public int totalSlices() {
        int count = 0;
        for (Pizza pizza : orderedPizzas) {
            count += pizza.getSlices();
        }
        return count;
    }
    
    @Override
    public String toString() {
        return "Solution{" +
                "orderedPizzas=" + orderedPizzas +
                '}';
    }

}
