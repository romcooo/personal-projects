package com.romco.pizzaChallenge;

import java.util.LinkedList;
import java.util.List;

public class Solver {
    private Requirement requirement;
    private List<PizzaNode> lastNodes = new LinkedList<>();
//    public static int counter = 0;
    
    public Solver(Requirement requirement) {
        this.requirement = requirement;
    }

    public String solve() {
        PizzaNode first = new PizzaNode(null, null);
        solveUntilEndOrDone(first, 0);
        PizzaNode best = SolverUtil.best(lastNodes);
        return SolverUtil.countNodes(best)
                + "\n"
                + SolverUtil.getAllPizzaTypes(best);
    }

    /** first, check if we are at end.
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

    // true means it reached the end of remainingList
    public boolean solveUntilEndOrDone(PizzaNode node, int startAtIndex) {
//        System.out.println(counter++);
        // if remainder is empty, we're at the end - add order and indicate end by "true"
        if (startAtIndex == requirement.getPizzas().size()) {
            if (node != null) {
                lastNodes.add(node);
            }
            return true;
        }
        // keep "going" through the list by increasing the pointer until we point at a pizza which still fits
        while (requirement.getPizzas().get(startAtIndex)
                          .getSlices() > SolverUtil.getMissingSlicesNode(requirement, node)) {
            startAtIndex++;
            if (startAtIndex == requirement.getPizzas().size()) {
                lastNodes.add(node);
                return true;
            }
        }
        // if we're not at the end, get next pizza
        Pizza nextPizza = requirement.getPizzas().get(startAtIndex);
        // if next pizza fits for us then add the pizza to our order
            PizzaNode newNode = new PizzaNode(node, nextPizza);
//            System.out.println("Adding pizza: " + SolverUtil.getAllPizzaString(newNode));
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
            // use the original list from that point after removing the originally added pizza, instead
            // using the following pizza
            if (solveUntilEndOrDone(newNode, startAtIndex)) {
//                System.out.println("last reached, now at : " + newNode.getAllPizzaString());
                // remove the originally added pizza
                PizzaNode previous = newNode.getPrevious();
//                System.out.println("last order reached end, proceeding with order: " + previous.getAllPizzaString());
//                System.out.println("remainder is" + beforeRemoval);
                // previous
                return solveUntilEndOrDone(previous, startAtIndex);
            }
            // if it doesn't reach the end, return false
            return false;
//            System.out.println(nextPizza.getSlices() + " is too big, need at most " + getMissingSlicesNode(node)
//                                       + " slices, going on, current order = " + node.getAllPizzaString());
    }
    
}
