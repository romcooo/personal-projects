package com.romco.pizzaChallenge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

class InputParser {
    public static Requirement parse() {
        Requirement requirement = new Requirement();
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        requirement.setMaxSlices(scanner.nextInt());
        requirement.setMaxNumberOfPizzas(scanner.nextInt());
        scanner.nextLine();
        short counter = 0;
        String line = scanner.nextLine();
        String[] lines = line.split(" ");
        scanner.close();
        for (String s : lines) {
            requirement.add(new Pizza(Integer.parseInt(s), counter));
            counter++;
        }
//        while(scanner.hasNextInt()) {
//            System.out.println(counter);
//            Pizza pizza = new Pizza(scanner.nextInt(), counter);
//            requirement.add(pizza);
//            counter++;
//        }
        return requirement;
    }
}
