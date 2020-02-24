<<<<<<< HEAD:bracketeer/core/src/main/java/com/romco/domain/MockDataModel.java
package com.romco.domain;
=======
package com.romco.domain.util;
>>>>>>> be88e0fe0ef736522434cea5e7abdfdce757de22:bracketeer/core/src2/main/java/com/romco/domain/util/MockDataModel.java

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
