package com.romco.persistence.dao;

import com.romco.domain.tournament.MatchResult;

import java.util.List;


public interface MatchResultDao extends GenericDao<MatchResult> {
    List<MatchResult> retrieveByMatchId(long matchId);
    MatchResult retrieveByParticipantIdAndMatchId(long participantId, long matchId);
}
