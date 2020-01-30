package com.romco;

import java.util.List;

public class NodeSolution {
    private PizzaNode lastNode;

    public NodeSolution(PizzaNode node) {
        this.lastNode = node;
    }

    public int totalSlices() {
        return lastNode.totalSlices();
    }

    @Override
    public String toString() {
        return "NodeSolution{" +
                "lastNode=" + lastNode +
                '}';
    }
}
