package com.romco.bracketeer.domain.matcher;

import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.tournament.Match;
import com.romco.bracketeer.domain.tournament.Round;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// package-private because TournamentFormat provides the preferred builder method
@Slf4j
class SwissMatcher implements Matcher {

    private int roundNumberToUse;
    private Round round;
    private int matchNumber = 0;
    
    @Override
    public Round generateRound(List<Participant> participants, SortMode mode, int numberOfRoundToGenerate) {
        if (participants.isEmpty()) {
            log.info("Empty list passed, returning null");
            return null;
        }
        roundNumberToUse = numberOfRoundToGenerate - 1;

        List<Participant> toPairList = mode.sort(participants, numberOfRoundToGenerate - 1);


        log.debug("toPairList after sorting: {}", toPairList);
        round = new Round();

        matchNumber = 1;
        if (MatcherHelper.handleBye(toPairList,
                                    round,
                                    matchNumber)) {
            matchNumber++;
        }

        match(toPairList);
    
        log.info("Matches: {}", round.getMatches().toString());
        return round;
    }

    @Override
    public int getMaxNumberOfRounds(int numberOfParticipants) {
        return numberOfParticipants - 1;
    }

    // todo this still assumes that the first player is the most important, which would kind of make sense since
    // the first player has the highest score always
    private boolean match(List<Participant> toPairList) {
        // if called with an empty list, returns true - this means success because everyone has been matched correctly
        if (toPairList.isEmpty()) {
            log.debug("toPairList empty, returning true");
            return true;
        }
        // take first
        Participant toMatch = toPairList.remove(0);

        // determine whom he has not yet played
        List<Participant> notPlayedYet = toPairList.stream()
                                                   .filter(p -> !toMatch.hasPlayedAgainstUptilRound(p, roundNumberToUse))
                                                   .collect(Collectors.toList());

        double scoreDiffThreshold;

        // 1. get scores
        // 2. filter out distinct values
        // 3. get the difference between current participant and the distinct score values
        // 4. get the absolute values (eg. a player with a score of 6 can be matched equally with a player with score of 3 as a player with score of 9)
        // 5. sort ascending
        List<Double> distinctDiffs = notPlayedYet.stream()
                                                 .map(p -> p.getScoreAfterRound(roundNumberToUse))
                                                 .distinct()
                                                 .map(distinctScore -> toMatch.getScoreAfterRound(roundNumberToUse) - distinctScore)
                                                 .map(Math::abs)
                                                 .sorted()
                                                 .collect(Collectors.toList());

        // go from the lowest diff and try to find if all the players can be matched with someone whom they haven't played
        while (!distinctDiffs.isEmpty()) {
            scoreDiffThreshold = distinctDiffs.get(0);
            List<Participant> scoreWithinThreshold = notPlayedYet.stream()
                                                                 .filter(isScoreWithinThreshold(roundNumberToUse,
                                                                                                toMatch.getScoreAfterRound(
                                                                                                        roundNumberToUse),
                                                                                                scoreDiffThreshold))
                                                                 .collect(Collectors.toList());

            log.debug("list of within threshold: {}", scoreWithinThreshold);

            for (Participant participant : scoreWithinThreshold) {
                List<Participant> newList = new ArrayList<>(toPairList);
                Participant opponent = participant;
                newList.remove(opponent);
                newList.remove(toMatch);

                Match match = new Match(toMatch, opponent);
                match.setMatchNumber(matchNumber);
                match.setOfRound(round);
                matchNumber++;
                round.addMatch(match);
                toMatch.setPlayedAgainstBiDirectional(opponent);
                log.debug("matched p1: {}, p2: {}, remaining: {}", toMatch, opponent, newList);
                if (match(newList)) {
                    return true;
                }
                // else keep going
            }
            // if none are matched successfully, increase threshold but just for this one participant TODO
            distinctDiffs.remove(0);
        }
        return false;
    }

    private static Predicate<Participant> isScoreWithinThreshold(int roundNumber, double origScore, double scoreThreshold) {
        return p -> p.getScoreAfterRound(roundNumber) >= origScore - scoreThreshold && p.getScoreAfterRound( roundNumber) <= origScore + scoreThreshold;
    }

}
