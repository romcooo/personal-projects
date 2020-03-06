package com.romco.persistence.dao;

import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.MatchResult;

import java.util.List;
import java.util.Map;


public interface MatchResultDao extends GenericDao<MatchResult> {
    Map<MatchResult, Long> retrieveByMatchId(long matchId);
    MatchResult retrieveByParticipantIdAndMatchId(long participantId, long matchId);
}
