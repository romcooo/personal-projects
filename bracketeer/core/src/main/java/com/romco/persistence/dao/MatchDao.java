package com.romco.persistence.dao;

import com.romco.domain.tournament.Match;
import com.romco.domain.tournament.Round;

import java.util.List;

public interface MatchDao extends GenericSimplePKDao<Match> {
    List<Match> retrieveByRoundId(long roundId);
}
