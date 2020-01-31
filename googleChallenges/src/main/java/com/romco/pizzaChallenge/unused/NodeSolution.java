package com.romco.pizzaChallenge.unused;

import com.romco.pizzaChallenge.Main;

public class NodeSolution {
    private Main.PizzaNode lastNode;

    public NodeSolution(Main.PizzaNode node) {
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
