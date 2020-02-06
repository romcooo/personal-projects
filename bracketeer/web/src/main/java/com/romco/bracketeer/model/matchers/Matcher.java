package com.romco.bracketeer.model.matchers;

import com.romco.bracketeer.model.Participant;
import com.romco.bracketeer.model.Round;

import java.util.List;

public interface Matcher {
    Round generateRound(List<Participant> participants);
}
