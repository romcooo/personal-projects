package com.romco.bracketeer.persistence.dao;

import com.romco.bracketeer.domain.tournament.Tournament;

public interface TournamentDao extends GenericSimplePKDao<Tournament> {
    Tournament retrieve(String code);
}
