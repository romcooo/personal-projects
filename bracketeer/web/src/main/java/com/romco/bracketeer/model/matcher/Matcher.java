package com.romco.bracketeer.model.matcher;

import com.romco.bracketeer.model.participant.Participant;
import com.romco.bracketeer.model.Round;

import java.util.List;

public interface Matcher {
    Round generateRound(List<Participant> participants);
}
