package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.participant.Participant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class Standings {

    private Standings() {}

    public static void printStandings(Tournament tournament) {
        printStandings(tournament, tournament.getRounds().size());
    }

    public static void printStandings(Tournament tournament, int afterRound) {
        List<Participant> participants = new ArrayList<>(tournament.getParticipants());

        participants.sort(Comparator.comparingDouble((Participant p) -> p.getScoreAfterRound(afterRound)).reversed());
        for (Participant participant : participants) {
            log.info(participant + " has score of: " + participant.getScore());
        }
    }
    
    public static List<Participant> getStandings(Tournament tournament, int afterRound) {
        List<Participant> participants = new ArrayList<>(tournament.getParticipants());
        
        participants.sort(Comparator.comparingDouble((Participant p) -> p.getScoreAfterRound(afterRound)).reversed());
        return participants;
    }

}
