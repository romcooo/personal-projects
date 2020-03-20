package com.romco.domain.matcher;

import com.romco.domain.participant.Participant;
import com.romco.domain.tournament.Round;

import java.util.List;

public interface Matcher {
    Round generateRound(List<Participant> participant, SortMode mode);
    int getMaxNumberOfRounds(int numberOfParticipants);
}
