package com.romco;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Getter
class Requirement {
    private int maxSlices, maxNumberOfPizzas;
    private List<Pizza> pizzas = new ArrayList<>();
    
    public Requirement() {
    }
    
    public Requirement(int maxSlices, int maxNumberOfPizzas, List<Pizza> pizzas) {
        this.maxSlices = maxSlices;
        this.maxNumberOfPizzas = maxNumberOfPizzas;
        this.pizzas = pizzas;
    }
    
    public int getMaxSlices() {
        return maxSlices;
    }
    
    public void setMaxSlices(int maxSlices) {
        this.maxSlices = maxSlices;
    }
    
    public int getMaxNumberOfPizzas() {
        return maxNumberOfPizzas;
    }
    
    public void setMaxNumberOfPizzas(int maxNumberOfPizzas) {
        this.maxNumberOfPizzas = maxNumberOfPizzas;
    }
    
    public List<Pizza> getPizzas() {
        return pizzas;
    }
    
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
    
    public void add(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void reverse() {
        Collections.reverse(pizzas);
    }
    
    @Override
    public String toString() {
        return "Requirement{" +
                "maxSlices=" + maxSlices +
                ", numberOfTypes=" + maxNumberOfPizzas +
                ", pizzas=" + pizzas +
                '}';
    }
}
