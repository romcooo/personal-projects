package com.romco.y2020qualification;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Requirement {
    private int numberOfBooks;
    private int numberOfLibraries;
    private int numberOfDays;
    private Map<String, Integer> bookScoreMap = new HashMap<>();
    private List<Library> libraries = new ArrayList<>();
    private List<String> booksToScan = new ArrayList<>();
    private List<Library> resultList = new ArrayList<>();

    public Requirement(int numberOfBooks, int numberOfLibraries, int numberOfDays, Map<String, Integer> bookScoreMap, List<String> booksToScan) {
        this.numberOfBooks = numberOfBooks;
        this.numberOfLibraries = numberOfLibraries;
        this.numberOfDays = numberOfDays;
        this.bookScoreMap = bookScoreMap;
        this.booksToScan = booksToScan;
    }


    public void addLibrary(Library library) {
        libraries.add(library);
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "numberOfBooks=" + numberOfBooks +
                ", numberOfLibraries=" + numberOfLibraries +
                ", numberOfDays=" + numberOfDays +
                ", libraries=" + libraries +
                ", booksToScan=" + booksToScan +
                ", resultList=" + resultList +
                '}';
    }

/*
    * notes:
    - order the book ids descendingly by score in each library before any recursion happens
    - recursive call to a method which gets the next best library
    * keep track of remaining days: rDays -= time to sign up library
    * keep track of books which will already be scanned
    * check the list of books and find the library which will score the highest (in case of tie, use one with more books)
    * remove the library from the list of
    *
*       -
    * */


    public void solve() {
        List<Library> libsToCheck = new ArrayList<>(libraries);
//        System.out.println("Unsorted libraries: " + libraries);

//        for (Library library : libraries) {
//            System.out.println(library);
//            library.getBookList().sort(Comparator.comparingInt(b -> {
//                if (bookScoreMap.get(b) != null) {
//                    return bookScoreMap.get(b);
//                } else {
//                    System.out.println(b);
//                    return 1;
//                }
//            }
//            ).reversed());
//        }

        for (Library library : libraries) {
//            System.out.println("Sorting library: " + library);
            library.getBookList().sort(Comparator.comparingInt(b -> bookScoreMap.get(b)).reversed());
        }

//        System.out.println("Sorted libraries: " + libraries);
        while (!booksToScan.isEmpty() && numberOfDays > 0 && !libsToCheck.isEmpty()) {
            Library nextBest = getBestLibrary(libsToCheck);
            if (nextBest != null) {
                numberOfDays -= nextBest.getSignupDays();
//                System.out.println("Number of days left after adding library " + nextBest + " is " + numberOfDays);
                resultList.add(nextBest);
                libsToCheck.remove(nextBest);
                booksToScan.removeAll(nextBest.getBookShipList());
            } else {
                // this means there is no other library to even consider
                break;
            }
        }
        System.out.println("result list:" + resultList);
        System.out.println("---------");
        StringBuilder sb = new StringBuilder();

        sb.append(resultList.size());

        for (Library lib : resultList) {
            sb.append("\n");
            sb.append(lib.getId())
              .append(" ")
              .append(lib.getBookShipList().size())
              .append("\n");

            for (String s : lib.getBookShipList()) {
                sb.append(s)
                  .append(" ");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println(sb.toString());
        System.out.println("-------");

        try{
            // Create new file
            String path="I:\\Git\\Personal\\personal-projects\\googleChallenges\\src\\main\\java\\com\\romco\\solution.txt";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    public Library getBestLibrary(List<Library> librariesToCheck) {
//        System.out.println("Checking libs: " + librariesToCheck);

        int bestScore = 0;
        Library bestLibrary = null;
        for (Library lib : librariesToCheck) {
            List<String> booksToShip = new ArrayList<>();
//            System.out.println("Checking library: " + lib);
            if (lib.getSignupDays() >= numberOfDays) {
//                System.out.println("removing library due to not enough days left: " + lib);
                libraries.remove(lib);
                continue;
            }
            int daysLeftAfter = numberOfDays - lib.getSignupDays();
            int libScore = 0;
            List<String> booksInLib = new ArrayList<>(lib.getBookList());
            if (lib.getBooksShippedPerDay() >= lib.getNumberOfBooks()) {
                for (int b = 0; b < lib.getNumberOfBooks(); b++) {
                    libScore += bookScoreMap.get(lib.getBookList().get(b));
                }
                booksToShip.addAll(lib.getBookList());
            } else {
                while (!booksInLib.isEmpty() && daysLeftAfter > 0) {
                    for (int d = 0; d < lib.getBooksShippedPerDay(); d++) {
                        // if lib is out of books
                        if (booksInLib.isEmpty()) {
                            break;
                        }
                        // get first book from ordered list
                        String bookId = booksInLib.get(0);
                        // if we still need the book, add score
                        if (booksToScan.contains(bookId)) {
                            libScore += bookScoreMap.get(bookId);
                            booksToShip.add(bookId);
//                    lib.addBookToShip(bookId);
//                        System.out.println("Adding book " + bookId + " to shipList of library, score is now: " + libScore + ", book will be shipped with " + daysLeftAfter + " days left");
                        }
                        // remove it regardless
                        booksInLib.remove(bookId);
                        // count down days
                    }
                    daysLeftAfter--;
                }
            }
//            System.out.println("libscore is: " + libScore);
            if (libScore >= bestScore) {
                lib.setBookShipList(booksToShip);
                if (bestLibrary != null) {
                    if (lib.getNumberOfBooks() > bestLibrary.getNumberOfBooks()) {
                        bestLibrary = lib;
                    }
                } else {
                    bestLibrary = lib;
                }
            }
            if (libScore < (bestScore / 3)) {
                librariesToCheck.remove(lib);
            }
        }
//        // clear ship list of the other libs
//        for (Library lib : librariesToCheck) {
//            if (lib != bestLibrary) {
//                lib.getBookShipList().clear();
//            }
//        }
        System.out.println("Best lib is: " + bestLibrary);
        return bestLibrary;
    }
}
