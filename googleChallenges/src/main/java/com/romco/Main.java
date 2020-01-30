package com.romco;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input = "17 4" +
                "\n3 5 7 8 11";
        System.out.println(input);
        Requirement requirement = InputParser.parse(input);
        System.out.println(requirement);
    
        Solver solver = new Solver(requirement);
        Solution best = solver.solve();
        System.out.println("solution is: " + best + ", with " + best.totalSlices() + " slices accross " + best.getNumberOfTypesOrdered() + " pizzas ordered.");
    }
}

