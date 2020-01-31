package com.romco.pizzaChallenge.unused;

import com.romco.pizzaChallenge.PizzaNode;

public class NodeSolution {
    private PizzaNode lastNode;

    public NodeSolution(PizzaNode node) {
        this.lastNode = node;
    }

//    public int totalSlices() {
//        return lastNode.totalSlices();
//    }

    @Override
    public String toString() {
        return "NodeSolution{" +
                "lastNode=" + lastNode +
                '}';
    }
}
