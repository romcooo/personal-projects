package com.romco.domain.tournament;

import com.romco.domain.participant.Participant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class Standings {

    public static void printStandings(Tournament tournament) {
        printStandings(tournament, tournament.getRounds().size());
    }

    public static void printStandings(Tournament tournament, int afterRoundNumber) {
        List<Participant> participants = new ArrayList<>(tournament.getParticipantsForAfterRound(afterRoundNumber));
        
        participants.sort(Comparator.comparingDouble(Participant::getScore).reversed());
        for (Participant participant : participants) {
            log.info(participant + " has score of: " + participant.getScore());
        }
    }



}
