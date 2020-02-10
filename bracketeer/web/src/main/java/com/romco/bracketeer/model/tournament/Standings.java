package com.romco.bracketeer.model.tournament;

import com.romco.bracketeer.model.participant.Participant;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Standings {

    public static void printStandings(Tournament tournament) {

        List<Participant> participants = new ArrayList<>(tournament.getParticipants());
        Map<Participant, Double> participantScores = new HashMap<>(tournament.getParticipantScores());

        participants.sort(Comparator.comparingDouble(participantScores::get).reversed());
        for (Participant participant : participants) {
            log.info(participant + " has score of: " + participantScores.get(participant));
        }
    }

}
