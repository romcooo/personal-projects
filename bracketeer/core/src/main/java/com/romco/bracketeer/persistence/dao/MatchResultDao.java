package com.romco.bracketeer.persistence.dao;

import com.romco.bracketeer.domain.tournament.MatchResult;

import java.util.Map;


public interface MatchResultDao extends GenericDao<MatchResult> {
    Map<MatchResult, Long> retrieveByMatchId(long matchId);
    MatchResult retrieveByParticipantIdAndMatchId(long participantId, long matchId);
}
