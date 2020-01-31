package com.romco.pizzaChallenge;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Solver {
    private Requirement requirement;
    private List<PizzaNode> lastNodes = new LinkedList<>();
    public static int counter = 0;
    
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

    public void solveNode() {
        PizzaNode first = new PizzaNode(null, null);
        solveNode(first, 0);
        SolverUtil.best(lastNodes);
    }


    // true means it reached the end of remainingList
    public boolean solveNode(PizzaNode node, int startAtIndex) {
        System.out.println(counter++);
//        counter++;
        // if remainder is empty, we're at the end - add order and indicate end by "1"
//        System.out.println("Start at:" + startAtIndex, "");
        if (startAtIndex == requirement.getPizzas().size()) {
            if (node != null) {
                lastNodes.add(node);
            }
            return true;
        }
        // if we're not at the end, get next pizza
        Pizza nextPizza = requirement.getPizzas().get(startAtIndex);
        // if next pizza fits for us then add the pizza to our order
        if (nextPizza.getSlices() <= (requirement.getMaxSlices() - SolverUtil.totalSlices(node))) {
            PizzaNode newNode = new PizzaNode(node, nextPizza);
//            System.out.println("Adding pizza: " + newNode.getAllPizzaString());
            // if we're not missing anything, we're done
            if (SolverUtil.getMissingSlicesNode(requirement, newNode) == 0) {
//                System.out.println("Perfect solution " + SolverUtil.getAllPizzaString(newNode)
//                                           + "\nTotal slices: " + SolverUtil.totalSlices(newNode)
//                                           + "\nNumber of types: " + SolverUtil.countNodes(newNode));
                lastNodes.add(newNode);
                return false;
            }
            // if we're capped with our pizzas, we're also done
            if (SolverUtil.countNodes(newNode) == requirement.getMaxNumberOfPizzas()) {
//                System.out.println("max slices in order: " + SolverUtil.getAllPizzaString(newNode)
//                                           + "\nTotal slices: " + SolverUtil.totalSlices(newNode)
//                                           + "\nNumber of types: " + SolverUtil.countNodes(newNode));
                lastNodes.add(newNode);
                return false;
            }
            // at this point, we added the pizza to our order. We need to remove the pizza we just added
            // then pass the remaining pizzas to be evaluated
            startAtIndex++;
            // however, if this run reaches the end, we want to repeat the entire check except we want to
            // use the original list from that point after removing the originally added pizza, instead using the following
            // pizza
            if (solveNode(newNode, startAtIndex)) {
//                System.out.println("last reached, now at : " + newNode.getAllPizzaString());
                // remove the originally added pizza
                PizzaNode previous = newNode.getPrevious();
//                System.out.println("last order reached end, proceeding with order: " + previous.getAllPizzaString());
//                System.out.println("remainder is" + beforeRemoval);
                // previous
                return solveNode(previous, startAtIndex);
            }
            // if it doesn't reach the end, return false
            return false;
        } else {
            // if the pizza is too big, remove it and keep going
//            System.out.println(nextPizza.getSlices() + " is too big, need at most " + getMissingSlicesNode(node)
//                                       + " slices, going on, current order = " + node.getAllPizzaString());
            return solveNode(node, ++startAtIndex);
        }
    }
    
}
