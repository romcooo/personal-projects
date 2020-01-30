package com.romco;

import java.util.*;

public class Solver {
    private Requirement requirement;
    
    public Solver(Requirement requirement) {
        this.requirement = requirement;
    }
    
    public Solution solve() {
        
        List<Pizza> pizzas = requirement.getPizzas();
        int maxSlices = requirement.getMaxSlices();
        int maxNumberOfPizzas = requirement.getMaxNumberOfPizzas();
        List<Solution> solutions = new ArrayList<>();
        boolean maxScore = false;
        while (!pizzas.isEmpty() && !maxScore) {
            int skipPointer = -1;
            int remainingSlices = maxSlices;
            int pizzaPointer = pizzas.size()-1;
            List<Pizza> pizzasToOrder = new ArrayList<>();
            while (skipPointer <= pizzas.size()) {
                if (pizzas.size() > 1) {
                    pizzaPointer = pizzas.size()-1;
                }
                while (pizzaPointer >= 0) {
//                    System.out.println("-- pizza pointer: " + pizzaPointer + ", skip pointer: " + skipPointer);
                    if (pizzaPointer == skipPointer) {
//                        System.out.println("--- skipping pizza: " + pizzas.get(skipPointer));
                        pizzaPointer--;
                        continue;
                    }
                    Pizza currentPizza = pizzas.get(pizzaPointer);
//                    System.out.println("-- at pizza: " + currentPizza);
                    if (currentPizza.getSlices() > remainingSlices) {
//                        System.out.println("--- too many slices: " + currentPizza.getSlices() + ", remaining: " + remainingSlices);
                        pizzaPointer--;
                        continue;
                    }
                    remainingSlices -= currentPizza.getSlices();
                    pizzasToOrder.add(currentPizza);
//                    System.out.println("-- adding pizza: " + currentPizza);
                    pizzaPointer--;
    
                    if (pizzasToOrder.size() == maxNumberOfPizzas) {
                        break;
                    }
                }
                remainingSlices = maxSlices;
                skipPointer++;
                if (!pizzasToOrder.isEmpty()) {
                    Solution currentSolution = new Solution(pizzasToOrder.size(), List.copyOf(pizzasToOrder));
                    solutions.add(currentSolution);
                    System.out.println("traversed list of size " + pizzas.size() + ", solution is: " + currentSolution);
                    pizzasToOrder.clear();
                    if (currentSolution.totalSlices() == maxSlices) {
                        maxScore = true;
                    }
                }
            }
            
            pizzas.remove(0);
            
        }
        
        Solution best = solutions.get(0);
        for (int i = 1; i < solutions.size(); i++) {
            if (solutions.get(i).totalSlices() > best.totalSlices()) {
                best = solutions.get(i);
            }
        }
        
        System.out.println(best);
        return best;
    }
}
