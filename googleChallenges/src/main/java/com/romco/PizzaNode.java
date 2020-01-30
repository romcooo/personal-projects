package com.romco;

public class PizzaNode {
    PizzaNode previous;
    Pizza pizza;

    public PizzaNode(PizzaNode previous, Pizza pizza) {
        this.previous = previous;
        this.pizza = pizza;
    }

    public PizzaNode getPrevious() {
        return previous;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public String printNodes() {
        StringBuilder sb = new StringBuilder();
        if (pizza != null) {
            sb.append(pizza.toString())
            .append(", ");
        }
        PizzaNode newNode = this.previous;
        while (newNode != null) {
            sb.append(newNode.getPizza())
              .append(", ");;
            newNode = newNode.getPrevious();
        }
        return sb.toString();
    }

    public int totalSlices() {
        if (this.pizza == null) {
            if (this.previous == null) {
                return 0;
            } else {
                return previous.totalSlices();
            }
        } else {
            if (this.previous == null) {
                return this.pizza.getSlices();
            } else {
                return this.pizza.getSlices() + previous.totalSlices();
            }
        }
//        if (this.pizza != null) {
//            slices += pizza.getSlices();
//        }
//        if (this.previous != null) {
//            slices += previous.totalSlices();
//        }
//        PizzaNode newNode = this.previous;
//        while (newNode != null) {
//            slices += newNode.getPizza().getSlices();
//            newNode = newNode.getPrevious();
//        }
//        return slices;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int count() {
        if (previous == null) {
            return 1;
        } else {
            return previous.count() + 1;
        }
    }

    @Override
    public String toString() {
        return "PizzaNode{" +
                "pizza=" + pizza +
                '}';
    }
}
