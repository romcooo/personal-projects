package com.romco.pizzaChallenge;

public class PizzaNode {
    PizzaNode previous;
    Pizza pizza;
//    public static int numberOfInstances;

    public PizzaNode(PizzaNode previous, Pizza pizza) {
//        numberOfInstances++;
        this.previous = previous;
        this.pizza = pizza;
    }

    public PizzaNode getPrevious() {
        return previous;
    }

    public Pizza getPizza() {
        return pizza;
    }
    
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String toString() {
        return "PizzaNode{" +
                "pizza=" + pizza +
                '}';
    }
}
