package com.romco.pizzaChallenge.unused;

import com.romco.pizzaChallenge.Main;

import java.util.List;

public class Solution {
    private List<Main.Pizza> orderedPizzas;
    
    public Solution(List<Main.Pizza> orderedPizzas) {
        this.orderedPizzas = List.copyOf(orderedPizzas);
    }


    public List<Main.Pizza> getOrderedPizzas() {
        return orderedPizzas;
    }
    
    public void setOrderedPizzas(List<Main.Pizza> orderedPizzas) {
        this.orderedPizzas = orderedPizzas;
    }
    
    public int totalSlices() {
        int count = 0;
        for (Main.Pizza pizza : orderedPizzas) {
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
