package com.romco.y2020qualification;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Requirement requirement = InputParser.parse();
        requirement.solve();
    }
}
