package com.romco.bracketeer.domain.matcher;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.Round;

import java.util.List;

// classes implementing this interface should preferrably be package-private
// because TournamentFormat provides the preferred builder method
public interface Matcher {
    Round generateRound(List<Participant> participants, SortMode mode, int numberOfRoundToGenerate);
    int getMaxNumberOfRounds(int numberOfParticipants);
}
