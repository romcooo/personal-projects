package com.romco.bracketeer.model;

import com.romco.bracketeer.model.tournament.Tournament;

import java.util.ArrayList;
import java.util.List;

public class MockDataModel {
    List<Tournament> tournamentList = new ArrayList<>();

    public MockDataModel() {
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void addTournament(Tournament tournament) {
        tournamentList.add(tournament);
    }

    public Tournament getByCode(int code) {
        return tournamentList.stream().filter((t) -> t.getId() == code).findFirst().get();
    }
}
