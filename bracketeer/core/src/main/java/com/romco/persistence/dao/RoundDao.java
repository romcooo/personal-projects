package com.romco.persistence.dao;

import com.romco.domain.tournament.Round;

import java.util.List;

public interface RoundDao extends GenericSimplePKDao<Round> {
    List<Round> retrieveByTournamentId(long tournamentId);
}
