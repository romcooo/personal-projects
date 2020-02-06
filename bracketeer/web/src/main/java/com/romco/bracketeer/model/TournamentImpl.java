package com.romco.bracketeer.model;

import com.romco.bracketeer.model.matchers.Matcher;
import com.romco.bracketeer.model.matchers.SwissMatcher;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TournamentImpl implements Tournament<Participant> {
    private List<Participant> participants;
    private List<Round> rounds;
    private TournamentType type;
    
    public TournamentImpl(TournamentType type) {
        this.type = type;
        this.participants = new ArrayList<>();
        this.rounds = new LinkedList<>();
        
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
        participants.sort(Comparator.comparingDouble(Participant::getScore));
        Collections.reverse(participants);
        log.info("Unsorted participants: {}", participants);
        
        Matcher matcher = new SwissMatcher();
        return matcher.generateRound(participants);
    }
    
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
    
    @Override
    public List<Match> getMatchesForParticipant(Participant participant) {
        return null;
    }
}
