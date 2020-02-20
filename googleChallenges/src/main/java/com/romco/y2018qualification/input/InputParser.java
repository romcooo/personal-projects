package com.romco.y2018qualification.input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {
    
    public static Requirement parse() {
        List<InputRide> rides = new ArrayList<>();
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int vehicles = scanner.nextInt();
        int numberOfRides = scanner.nextInt();
        int startOnTimeBonus = scanner.nextInt();
        int simulationSteps = scanner.nextInt();
        scanner.nextLine();
        scanner.useDelimiter("\n");
    
        for (int l = 0; l < numberOfRides; l++) {
            String[] vals = scanner.next().split(" ");
            rides.add(new InputRide(l,
                                    Integer.parseInt(vals[0]),
                                    Integer.parseInt(vals[1]),
                                    Integer.parseInt(vals[2]),
                                    Integer.parseInt(vals[3]),
                                    Integer.parseInt(vals[4]),
                                    Integer.parseInt(vals[5])));
            scanner.nextLine();
        }
        
//        int counter = 0;
//        while (counter < numberOfRides) {
//            rides.add(scanner.next());
//            scanner.nextLine();
//            counter++;
//        }
    
//        System.out.println(rides);
        
        Requirement requirement = new Requirement(rows, cols, vehicles, numberOfRides, startOnTimeBonus, simulationSteps, rides);
        
        scanner.close();
        System.out.println(requirement);
        
        return requirement;
    }
}