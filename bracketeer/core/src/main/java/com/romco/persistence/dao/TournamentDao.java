package com.romco.persistence.dao;

import com.romco.domain.tournament.Tournament;

public interface TournamentDao extends GenericDao<Tournament> {
    Tournament retrieve(String code);
}
