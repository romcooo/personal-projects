package com.romco.bracketeer.persistence.dao;

import com.romco.bracketeer.domain.participant.Participant;

import java.util.List;

public interface ParticipantDao extends GenericSimplePKDao<Participant> {
    List<Participant> retrieveByTournamentId(long tournamentId);
}
