package com.romco.y2020qualification;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;

public class InputParser {

    public static Requirement parse() throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(
                new FileInputStream("I:\\Git\\Personal\\personal-projects\\googleChallenges\\src\\main\\java\\com\\romco\\y2020qualification\\input\\d_tough_choices.txt"))));
        int numberOfBooks = scanner.nextInt();
        int numberOfLibraries = scanner.nextInt();
        int numberOfDays = scanner.nextInt();
        scanner.nextLine();

        List<String> bookIds = new ArrayList<>();
        Map<String, Integer> bookScoreMap = new HashMap<>();
        // book scores
//        System.out.println(numberOfBooks);
        for (int b = 0; b < numberOfBooks; b++) {
            int bookScore = scanner.nextInt();
            bookScoreMap.put(Integer.toString(b), bookScore);
            bookIds.add(Integer.toString(b));
//            System.out.println("bookid: " + b);
        }
        Requirement requirement = new Requirement(numberOfBooks, numberOfLibraries, numberOfDays, bookScoreMap, bookIds);

        // libraries
        scanner.nextLine();
//        scanner.useDelimiter("\n");

        for (int l = 0; l < numberOfLibraries; l++) {
            int numberOfBooksInLibrary = scanner.nextInt();
            int numberOfDaysToSignUp = scanner.nextInt();
            int numberOfBooksShippedPerDay = scanner.nextInt();

            Library library = new Library(l, numberOfBooksInLibrary, numberOfDaysToSignUp, numberOfBooksShippedPerDay);

            scanner.nextLine();

            for (int b = 0; b < numberOfBooksInLibrary; b++) {
                String bookId = String.valueOf(scanner.nextInt());
                if (bookIds.contains(bookId)) {
                    library.addBookToList(bookId);
                }
            }
            System.out.println("About to add library: " + library);
            requirement.addLibrary(library);
        }

//        int counter = 0;
//        while (counter < numberOfRides) {
//            rides.add(scanner.next());
//            scanner.nextLine();
//            counter++;
//        }

//        System.out.println(rides);


        scanner.close();
        System.out.println(requirement);

        return requirement;
    }

}
