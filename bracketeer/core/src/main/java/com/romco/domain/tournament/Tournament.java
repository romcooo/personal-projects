package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;

import java.util.List;

public interface Tournament {
    long getId();
    void setId(long id);
    
    String getCode();
    void setCode(String code);
    
    String getName();
    void setName(String name);

    RuleSet getRuleSet();
    void setRuleSet(RuleSet ruleSet);
    
    boolean addParticipant(Participant participant);
    Participant removeParticipant(long id);

    List<Participant> getParticipants();
    List<Participant> getParticipantsAfterRound(int roundNumber);


    void setRounds(List<Round> rounds);
    
    Round generateNextRound();
    Round generateRound(int n);
    int getMaxNumberOfRounds();

    Round getRound(int roundNumber);
    List<Round> getRounds();

    List<MatchResult> setMatchResult(int roundNumber, Participant participant, int gamesWon, int gamesLost);
    List<MatchResult> setMatchResult(int roundNumber, String participantCode, int gamesWon, int gamesLost);
    List<MatchResult> setMatchResult(int roundNumber, String participantCode, int gamesWon);

    void setStartingScore(Participant participant, double by);
}
