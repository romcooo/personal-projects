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

    public String getAllPizzaString() {
        StringBuilder sb = new StringBuilder();
        PizzaNode node = this;
        while (node.getPrevious() != null) {
            if (node.getPizza() != null) {
                sb.append(node.getPizza())
                  .append(", ");
            }
            node = node.getPrevious();
        }
        if (sb.lastIndexOf(",") != -1) {
            sb.delete(sb.lastIndexOf(","), sb.length());
        } else {
            sb.append("Empty");
        }
        return sb.toString();
    }

    public int totalSlices() {
        int slices = 0;
        PizzaNode node = this;
        while (node.getPizza() != null) {
            slices += node.getPizza().getSlices();
            if (node.getPrevious() != null) {
                node = node.getPrevious();
            } else {
                break;
            }
        }
        return slices;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int count() {
        int count = 1;
        PizzaNode node = this;
        while (node.getPrevious() != null) {
            count++;
            node = node.getPrevious();
        }
        return count;
//        if (previous == null) {
//            return 1;
//        } else {
//            return previous.count() + 1;
//        }
    }

    @Override
    public String toString() {
        return "PizzaNode{" +
                "pizza=" + pizza +
                '}';
    }
}
