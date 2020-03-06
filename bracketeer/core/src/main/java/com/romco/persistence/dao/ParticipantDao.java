package com.romco.persistence.dao;

import com.romco.domain.participant.Participant;

import java.util.List;

public interface ParticipantDao extends GenericSimplePKDao<Participant> {
    List<Participant> retrieveByTournamentId(long tournamentId);
}
