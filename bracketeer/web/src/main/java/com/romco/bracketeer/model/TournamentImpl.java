package com.romco.bracketeer.model;

import com.romco.bracketeer.model.matcher.Matcher;
import com.romco.bracketeer.model.matcher.TournamentFormat;
import com.romco.bracketeer.model.participant.Participant;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Part;
import java.util.*;

@Slf4j
public class TournamentImpl implements Tournament {
    private List<Participant> participants;
    private List<Round> rounds;
    private TournamentFormat type;
    private RuleSet ruleSet;
    
    /**
     * Creates a TournamentImpl with a RuleSet.getDefaultRuleSet()
     * @param type - TournamentType (Swiss, Round Robin, Single Elim, Double Elim)
     */
    public TournamentImpl(TournamentFormat type) {
        this.type = type;
        this.participants = new ArrayList<>();
        this.rounds = new LinkedList<>();
        this.ruleSet = RuleSet.getDefaultRuleSet();
    }
    
    public int addParticipants(List<Participant> participants) {
        int count = 0;
        for (Participant participant : participants) {
            addParticipant(participant);
            count++;
        }
        return count;
    }
    
    @Override
    public boolean addParticipant(Participant participant) {
        if (participants.contains(participant)) {
            log.info("Tournament already contains participant {}", participant);
            return false;
        } else {
            log.info("Adding participant {}", participant);
            participants.add(participant);
            return true;
        }
    }
    
    @Override
    public boolean removeParticipant(Participant participant) {
        return false;
    }
    
    @Override
    public Round generateNextRound() {
        log.info("Unsorted participants: {}", participants);
        
        Matcher matcher = type.buildMatcher();
        Round round = matcher.generateRound(participants);
        rounds.add(round);
        return round;
    }
    
    /**
     * Returns round of specified number (first index is 0)
     * @param n - number of round
     * @return - nth round
     */
    @Override
    public Round getRound(int n) {
        return rounds.get(n);
    }
    
    public List<Round> getRounds() {
        return rounds;
    }
    
    public Participant getParticipant(int n) {
        return participants.get(n);
    }
    
    public boolean setMatchResult(int roundNumber,
                                  Participant participant,
                                  int gamesWon,
                                  int gamesLost) {
        Match match = rounds.get(roundNumber).getMatch(participant);
        
        match.setResult(participant,
                         gamesWon,
                         gamesLost);
    
        Participant other = match.getOther(participant);
        
        if (gamesWon > gamesLost) {
            participant.updateScore(3);
        }
        if (gamesWon < gamesLost) {
            other.updateScore(3);
        }
        if (gamesWon == gamesLost) {
            participant.updateScore(1);
            other.updateScore(1);
        }
        
        return false;
    }
    
    public void printStandings() {
        participants.sort(Comparator.comparingDouble(Participant::getScore).reversed());
        for (int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            System.out.println(participant);
        }
    }
}
