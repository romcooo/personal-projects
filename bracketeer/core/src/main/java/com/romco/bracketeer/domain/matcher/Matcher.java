package com.romco.bracketeer.domain.matcher;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.Round;

import java.util.List;

public interface Matcher {
    Round generateRound(List<Participant> participants, SortMode mode);
    int getMaxNumberOfRounds(int numberOfParticipants);
}
