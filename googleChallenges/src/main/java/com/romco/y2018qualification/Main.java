package com.romco.y2018qualification;

import com.romco.y2018qualification.input.InputParser;
import com.romco.y2018qualification.input.Requirement;

public class Main {
    public static void main(String[] args) {
        Requirement requirement = InputParser.parse();
        requirement.solve();
    }
}
