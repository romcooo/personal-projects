package com.romco.bracketeer.model.tournament;

import com.romco.bracketeer.model.participant.Participant;

import java.util.List;
import java.util.Map;

public interface Tournament {
    String getName();
    void setName(String name);
    boolean addParticipant(Participant participant);
    boolean removeParticipant(int id);

    List<Participant> getParticipants();
    Round getRound(int n);

    Round generateNextRound();

    boolean setMatchResult(int roundNumber, Participant participant, int gamesWon, int gamesLost);
    boolean setMatchResult(int roundNumber, int participantId, int gamesWon, int gamesLost);
    void setStartingScore(Participant participant, double by);
}
