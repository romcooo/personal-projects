package com.romco.bracketeer.model.matcher;

import com.romco.bracketeer.model.Round;
import com.romco.bracketeer.model.participant.Participant;

import java.util.Map;

public interface Matcher {
    Round generateRound(Map<Participant, Double> participantMap);
}
