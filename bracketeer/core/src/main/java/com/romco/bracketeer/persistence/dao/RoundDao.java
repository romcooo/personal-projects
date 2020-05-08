package com.romco.bracketeer.persistence.dao;

import com.romco.bracketeer.domain.tournament.Round;

import java.util.List;

public interface RoundDao extends GenericSimplePKDao<Round> {
    List<Round> retrieveByTournamentId(long tournamentId);
}
