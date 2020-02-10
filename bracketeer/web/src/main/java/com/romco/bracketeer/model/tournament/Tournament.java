package com.romco.bracketeer.model.tournament;

import com.romco.bracketeer.model.participant.Participant;

import java.util.List;
import java.util.Map;

public interface Tournament {
    boolean addParticipant(Participant participant);
    boolean removeParticipant(Participant participant);

    Map<Participant, Double> getParticipantScores();
    Map<Participant, Integer> getParticipantByes();
    List<Participant> getParticipants();
    Round getRound(int n);

    Round generateNextRound();

    boolean setMatchResult(int roundNumber, Participant participant, int gamesWon, int gamesLost);
    void setStartingScore(Participant participant, double by);
}
