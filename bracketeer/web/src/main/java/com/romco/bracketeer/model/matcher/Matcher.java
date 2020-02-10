package com.romco.bracketeer.model.matcher;

import com.romco.bracketeer.model.tournament.Round;
import com.romco.bracketeer.model.participant.Participant;

import java.util.List;
import java.util.Map;

public interface Matcher {
    Round generateRound(List<Participant> participant);
}
