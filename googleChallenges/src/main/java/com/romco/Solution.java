package com.romco;

import java.util.List;

public class Solution {
    private int numberOfTypesOrdered;
    private List<Pizza> orderedPizzas;
    
    public Solution(int numberOfTypesOrdered, List<Pizza> orderedPizzas) {
        this.numberOfTypesOrdered = numberOfTypesOrdered;
        this.orderedPizzas = orderedPizzas;
    }
    
    public int getNumberOfTypesOrdered() {
        return numberOfTypesOrdered;
    }
    
    public void setNumberOfTypesOrdered(int numberOfTypesOrdered) {
        this.numberOfTypesOrdered = numberOfTypesOrdered;
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
                "numberOfTypesOrdered=" + numberOfTypesOrdered +
                ", orderedPizzas=" + orderedPizzas +
                '}';
    }

}
