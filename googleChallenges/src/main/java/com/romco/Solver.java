package com.romco;

import java.util.*;

public class Solver {
    private Requirement requirement;
    private List<Solution> solutions = new ArrayList<>();
    
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
        List<Pizza> pizzas = new ArrayList<>();
        solve(pizzas, requirement.getPizzas());
        Solution best = solutions.get(0);
        for (int i = 1; i < solutions.size(); i++) {
            if (solutions.get(i).totalSlices() > best.totalSlices()) {
                best = solutions.get(i);
            }
        }
        System.out.println(best);
    }

    public int solve(List<Pizza> order, List<Pizza> remainingList) {
        if (remainingList.isEmpty()) {
            List<Pizza> finalOrder = new ArrayList<>(List.copyOf(order));
            System.out.println("at end: " + finalOrder);
            if (!finalOrder.isEmpty()) {
                solutions.add(new Solution(finalOrder));
            }
            return 1;
        }
        Pizza next = remainingList.get(0);
        if (next.getSlices() <= getMissingSlices(order)) {
            List<Pizza> beforeRemoval = new ArrayList<>(List.copyOf(remainingList));
//            System.out.println("Adding " + next.getSlices() + " to order " + order);
            order.add(next);
            if (getMissingSlices(order) == 0) {
                System.out.println("Perfect solution " + order);
                solutions.add(new Solution(order));
                return 2;
            }
            if (order.size() == requirement.getMaxNumberOfPizzas()) {
                System.out.println("max slices in order: " + order);
                solutions.add(new Solution(order));
                return 0;
            }
            remainingList.remove(0);
            if (solve(order, remainingList) == 1) {
                beforeRemoval.remove(0);
                order.remove(order.size()-1);
                System.out.println("last order reached end, proceeding with order: " + order);
                System.out.println("remainder is" + beforeRemoval);
                return solve(order, beforeRemoval);
            }
        } else {
            System.out.println(next.getSlices() + " is too big, need at most " + getMissingSlices(order) + " slices, going on, current order = " + order);
            remainingList.remove(0);
            return solve(order, remainingList);
        }
        return 0;
    }

    public int getMissingSlices(List<Pizza> order) {
        int slices = 0;
        for (Pizza pizza : order) {
            slices += pizza.getSlices();
        }
        return requirement.getMaxSlices() - slices;
    }
}
