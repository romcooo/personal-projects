package com.romco;

import java.util.*;

public class Solver {
    private Requirement requirement;
    private List<Solution> solutions = new Vector<>();
    private List<PizzaNode> lastNodes = new Vector<>();
    static int counter = 0;
    
    public Solver(Requirement requirement) {
        this.requirement = requirement;
    }

    /* first, check if we are at end.
        If yes, register order into solutions and indicate end.
        if not, check if the first of the remaining can be added.
            if yes, add it to order then check if we are solved
                if yes, return completely
                else check if order is full
                    if yes, end
                    else recurse on remainder with that order
                        if the remainder reached end, recurse on remainder without the last addition
            else, recurse on remainder

     */

    public void solve() {
        List<Pizza> pizzas = new Vector<>();
        solve(pizzas, requirement.getPizzas());
        Solution best = solutions.get(0);
        for (int i = 1; i < solutions.size(); i++) {
            if (solutions.get(i).totalSlices() > best.totalSlices()) {
                best = solutions.get(i);
            }
        }
        System.out.println(best);
    }

    public void solveNode() {
        PizzaNode first = new PizzaNode(null, new Pizza(0, -1));
        solveNode(first, requirement.getPizzas());
//        for (PizzaNode node : lastNodes) {
//            if (node.get)
//            System.out.println(node.getAllPizzaString());
//        }
    }

    // true means it reached the end of remainingList
    public boolean solve(List<Pizza> order, List<Pizza> remainingList) {
//        System.out.println(counter++);
        // if remainder is empty, we're at the end - add order and indicate end by "1"
        
        if (remainingList.isEmpty()) {
//            System.out.println("at end: " + order);
            if (!order.isEmpty()) {
                solutions.add(new Solution(new Vector<>(List.copyOf(order))));
                order = null;
            }
            return true;
        }
        // if we're not at the end, get next pizza
        Pizza next = remainingList.get(0);
        // if next pizza fits for us then add the pizza to our order
        if (next.getSlices() <= getMissingSlices(order)) {
            order.add(next);
            // if we're not missing anything, we're done
            if (getMissingSlices(order) == 0) {
                System.out.println("Perfect solution " + order);
                solutions.add(new Solution(order));
                return false;
            }
            // if we're capped with our pizzas, we're also done
            if (order.size() == requirement.getMaxNumberOfPizzas()) {
//                System.out.println("max slices in order: " + order);
                solutions.add(new Solution(order));
                return false;
            }
            // at this point, we added the pizza to our order. We need to remove the pizza we just added
            // then pass the remaining pizzas to be evaluated
            remainingList.remove(0);
            List<Pizza> beforeRemoval = new Vector<>(List.copyOf(remainingList));

            // however, if this run reaches the end, we want to repeat the entire check except we want to
            // use the original list from that point after removing the originally added pizza, instead using the following
            // pizza
            if (solve(order, remainingList)) {
                // remove the originally added pizza
                order.remove(order.size()-1);
                System.out.println("last order reached end, proceeding with order: " + order);
//                System.out.println("remainder is" + beforeRemoval);
                // then go on again, on the remainder of the original list
                return solve(order, beforeRemoval);
            }
            // if it doesn't reach the end, return false
            return false;
        } else {
            // if the pizza is too big, remove it and keep going
//            System.out.println(next.getSlices() + " is too big, need at most " + getMissingSlices(order) + " slices, going on, current order = " + order);
            remainingList.remove(0);
            return solve(order, remainingList);
        }
    }

    // true means it reached the end of remainingList
    public boolean solveNode(PizzaNode node, List<Pizza> remainingList) {
        System.out.println(counter++);
        // if remainder is empty, we're at the end - add order and indicate end by "1"
        if (remainingList.isEmpty()) {
//            System.out.println("at end: " + order);
            if (node != null) {
                lastNodes.add(node);
                return true;
            }
        }
        // if we're not at the end, get next pizza
        Pizza nextPizza = remainingList.get(0);
        // if next pizza fits for us then add the pizza to our order
        if (nextPizza.getSlices() <= getMissingSlicesNode(node)) {
        
//            node.setPizza(nextPizza);
    
            PizzaNode newNode = new PizzaNode(node, nextPizza);
//            System.out.println(newNode.getAllPizzaString());
            
            // if we're not missing anything, we're done
            if (getMissingSlicesNode(newNode) == 0) {
                System.out.println("Perfect solution " + newNode.getAllPizzaString());
                System.out.println(newNode.totalSlices());
                lastNodes.add(newNode);
                return false;
            }
            // if we're capped with our pizzas, we're also done
            if (newNode.count() == requirement.getMaxNumberOfPizzas()) {
                System.out.println("max slices in order: " + newNode.getAllPizzaString());
                lastNodes.add(newNode);
                return false;
            }
            // at this point, we added the pizza to our order. We need to remove the pizza we just added
            // then pass the remaining pizzas to be evaluated
            remainingList.remove(0);
            List<Pizza> beforeRemoval = new Vector<>(List.copyOf(remainingList));
            // however, if this run reaches the end, we want to repeat the entire check except we want to
            // use the original list from that point after removing the originally added pizza, instead using the following
            // pizza
            if (solveNode(newNode, remainingList)) {
                // remove the originally added pizza
//                System.out.println("last reached, now at : " + newNode.getAllPizzaString());
//                newNode = newNode.getPrevious();
                PizzaNode previous = newNode.getPrevious();
                System.out.println("last order reached end, proceeding with order: " + previous.getAllPizzaString());
//                System.out.println("remainder is" + beforeRemoval);
                // previous
                return solveNode(previous, beforeRemoval);
            }
            // if it doesn't reach the end, return false
            return false;
        } else {
            // if the pizza is too big, remove it and keep going
//            System.out.println(nextPizza.getSlices() + " is too big, need at most " + getMissingSlicesNode(node)
//                                       + " slices, going on, current order = " + node.getAllPizzaString());
            remainingList.remove(0);
            return solveNode(node, remainingList);
        }
    }

    public int getMissingSlices(List<Pizza> order) {
        int slices = 0;
        for (Pizza pizza : order) {
            slices += pizza.getSlices();
        }
        return requirement.getMaxSlices() - slices;
    }

    public int getMissingSlicesNode(PizzaNode node) {
        int slices = 0;
//        if (node.getPizza() != null) {
//            slices += node.getPizza().getSlices();
//        }
//        while (node.getPrevious() != null) {
//            node = node.getPrevious();
//            if (node.getPizza() != null) {
//                slices += node.getPizza().getSlices();
//            }
//        }
        slices += node.totalSlices();
        return requirement.getMaxSlices() - slices;
    }

}
