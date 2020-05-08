package com.romco.bracketeer.persistence.dao;

import com.romco.bracketeer.domain.tournament.Match;

import java.util.List;

public interface MatchDao extends GenericSimplePKDao<Match> {
    List<Match> retrieveByRoundId(long roundId);
}
