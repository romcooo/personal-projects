package com.romco.domain;

import com.romco.domain.tournament.Tournament;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        log.debug("In getByCode (mock) with code {}", code);
        for (Tournament tournament : tournamentList) {
            System.out.println(tournament);
        }
        return tournamentList.stream().filter((t) -> t.getId() == code).findFirst().get();
    }
}
