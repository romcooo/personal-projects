package com.romco.pizzaChallenge;

import java.util.List;

public class SolverUtil {
    public static int getMissingSlicesNode(Requirement requirement, PizzaNode node) {
        return requirement.getMaxSlices() - totalSlices(node);
    }
    
    public static int totalSlices(PizzaNode node) {
        int slices = 0;
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
    
    public static String getAllPizzaString(PizzaNode node) {
        if (node == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
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
    
    public static int countNodes(PizzaNode node) {
        int count = 0;
        while (node.getPrevious() != null) {
            count++;
            node = node.getPrevious();
        }
        return count;
    }
    
    public static PizzaNode best(List<PizzaNode> nodes) {
        int sliceCount = 0;
        PizzaNode best = null;
        for (PizzaNode node : nodes) {
            if (totalSlices(node) > sliceCount) {
                sliceCount = totalSlices(node);
                best = node;
            }
        }
//        System.out.println("best node is: " + getAllPizzaString(best)
//                                   + "\nwith slice count: " + sliceCount);
        return best;
    }

    public static String getAllPizzaTypes(PizzaNode node) {
        StringBuilder sb = new StringBuilder();
        while (node.getPrevious() != null) {
            if (node.getPizza() != null) {
                sb.append(node.getPizza().getType())
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
}
