package com.romco;

import lombok.Data;

import java.util.Scanner;

class InputParser {
    public static Requirement parse(String input) {
        Requirement requirement = new Requirement();
        Scanner scanner = new Scanner(input);
        requirement.setMaxSlices(scanner.nextInt());
        requirement.setMaxNumberOfPizzas(scanner.nextInt());
        scanner.nextLine();
        short counter = 0;
        while(scanner.hasNextInt()) {
            Pizza pizza = new Pizza(scanner.nextInt(), counter);
            requirement.add(pizza);
            counter++;
        }
        return requirement;
    }
}
