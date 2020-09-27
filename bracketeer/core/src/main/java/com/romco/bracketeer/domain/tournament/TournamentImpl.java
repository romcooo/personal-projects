package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.matcher.Matcher;
import com.romco.bracketeer.domain.matcher.SortMode;
import com.romco.bracketeer.domain.matcher.TournamentFormat;
import com.romco.bracketeer.domain.participant.Participant;
import com.romco.bracketeer.domain.util.CodeGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@Getter
@Setter
public class TournamentImpl implements Tournament {
    private static int idCounter;

    private long id;
    private String code; // business id
    private String name;

    // TODO move type to ruleSet
//    private TournamentFormat type;
    private RuleSet ruleSet;

    private List<Round> rounds;

    private List<Participant> participants;
    private Map<Participant, Integer> startingParticipantByes;
    private Map<Participant, Double> startingParticipantScores;

    private ParticipantManager participantManager;

    // == CONSTRUCTORS

    public TournamentImpl() {
        this.id = idCounter++;
        this.code = CodeGenerator.getCode();
        this.name = "";
        this.ruleSet = RuleSet.getDefaultRuleSet();

        this.participants = new ArrayList<>();
        this.startingParticipantScores = new HashMap<>();
        this.startingParticipantByes = new HashMap<>();

        this.rounds = new LinkedList<>();

        this.participantManager = new ParticipantManager();
    }

    /**
     * Creates a TournamentImpl with a RuleSet.getDefaultRuleSet()
     * @param type - TournamentType (Swiss, Round Robin, Single Elim, Double Elim)
     */
    public TournamentImpl(TournamentFormat type) {
        this();
//        this.type = type;
        ruleSet.setType(type);
    }
    
    public TournamentImpl(TournamentFormat type, String name) {
        this(type);
        this.name = name;
    }
    
    public TournamentImpl(String code, String name, TournamentFormat type) {
        this();
        this.code = code;
        this.name = name;
//        this.type = type;
        ruleSet.setType(type);
    }
    
    public TournamentImpl(long id,
                          String code,
                          String name,
                          List<Participant> participants,
                          List<Round> rounds,
                          TournamentFormat type,
                          RuleSet ruleSet) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.participants = participants;
        this.startingParticipantScores = startingParticipantScores;
        this.startingParticipantByes = startingParticipantByes;
        this.rounds = rounds;
//        this.type = type;
        ruleSet.setType(type);
        this.ruleSet = ruleSet;
    }
    
    // TODO maybe implement later
//    public int addParticipants(List<Participant> participants) {
//        int count = 0;
//        for (Participant participant : participants) {
//            addParticipant(participant);
//            count++;
//        }
//        return count;

//    }

    public void setStartingScore(Participant participant, double score) {
        startingParticipantScores.put(participant, score);
    }

    @Override
    public Round generateNextRound() {
        log.info("Unsorted participants: {}", participants);
        return generateRound(rounds.size()+1);
    }

    @Override
    public Round generateRound(int roundNumber) {
        updateParticipantsForAfterRound(roundNumber - 1);

        Matcher matcher = ruleSet.getType().buildMatcher();

        Round round = matcher.generateRound(Collections.unmodifiableList(participants),
                                            SortMode.SHUFFLE_THEN_SORT);
        round.setRoundNumber(rounds.size()+1);
        round.setOfTournament(this);
        rounds.add(round);

        return round;
    }

    @Override
    public int getMaxNumberOfRounds() {
        log.debug("In getMaxNumberOfRounds, ruleSet: {}, participants: {}", ruleSet, participants);
        return ruleSet.getType()
                      .buildMatcher()
                      .getMaxNumberOfRounds(participants.size());
    }

    /**
     * Returns round of specified number (first index is 0)
     * @param roundNumber - number of round
     * @return - nth round
     */
    @Override
    public Round getRound(int roundNumber) {
        Optional<Round> round = rounds.stream().filter((r) -> r.getRoundNumber() == roundNumber).findFirst();
        if (round.isEmpty()) {
            log.info("round of number {} doesn't exist", roundNumber);
            return null;
        }
        return round.get();
    }

    @Override
    public List<MatchResult> setMatchResult(int roundNumber,
                                  Participant participant,
                                  int gamesWon,
                                  int gamesLost) {
        return getRound(roundNumber).getMatchByParticipant(participant)
                                    .setMatchScore(participant,
                                                   gamesWon,
                                                   gamesLost);

//        return Collections.emptyList();
    }

    @Override
    public List<MatchResult> setMatchResult(int roundNumber,
                                  String participantCode,
                                  int gamesWon,
                                  int gamesLost) {
        for (Participant participant : participants) {
            if (participant.getCode().equals(participantCode)) {
                log.debug("in setMatchResult, setting match result for participant: {}, {}", participant, gamesWon);
                return setMatchResult(roundNumber, participant, gamesWon, gamesLost);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MatchResult> setMatchResult(int roundNumber,
                                  String participantCode,
                                  int gamesWon) {

            for (Participant participant : participants) {
                if (participant.getCode().equals(participantCode)) {
                    return getRound(roundNumber).getMatchByParticipant(participant)
                                                .setMatchScore(participant, gamesWon);
                }
            }
        return Collections.emptyList();
    }

    @Override
    public List<Participant> getParticipants() {
        updateParticipants();
        return Collections.unmodifiableList(participants);
    }

    @Override
    public boolean addParticipant(Participant participant) {
        if (participants.contains(participant)) {
            log.info("Tournament already contains participant {}", participant);
            return false;
        } else {
            participants.add(participant);
            participant.setCode(Integer.toString(participants.size()));
            startingParticipantScores.put(participant, 0d);
            startingParticipantByes.put(participant, 0);
            participant.setOfTournament(this);
            log.info("Adding participant {}", participant);
            return true;
        }
    }

    @Override
    public Participant removeParticipant(long id) {
        for (Participant participant : this.participants) {
            if (participant.getId() == id) {
                participant.setOfTournament(null);
                participants.remove(participant);
                reconcileParticipantCodes();
                return participant;
            }
        }
        return null;
    }


    @Override
    public List<Participant> getParticipantsAfterRound(int roundNumber) {
        updateParticipantsForAfterRound(roundNumber);
        return Collections.unmodifiableList(participants);
    }

    public Double getParticipantScore(Participant participant) {
        log.debug("In getParticipantScore, rounds: {}", rounds);
        return getParticipantScoreAfterRound(participant, rounds.size());
    }

    public Double getParticipantScoreAfterRound(Participant participant, int roundNumber) {
        double score = startingParticipantScores.get(participant);
        for (Round round : rounds) {
            if (round.getRoundNumber() > roundNumber) {
                continue;
            }
            Match match = round.getMatchByParticipant(participant);
            if (match != null) {
                MatchResultEnum result = match.getMatchResult(participant);
                if (result != null) {
                    score += ruleSet.getPoints(result);
                    log.debug("Result for participant {} is {}, score set to {}", participant, result, score);
                }
            }
        }
        log.debug("Score of participant {} is {} after round {}", participant, score, roundNumber);
        return score;
    }

    public Integer getParticipantByes(Participant participant) {
        return getParticipantByesAfterRound(participant, rounds.size());
    }

    public Integer getParticipantByesAfterRound(Participant participant, int roundNumber) {
        int byes = startingParticipantByes.get(participant);
        for (Round round : rounds) {
            if (round.getRoundNumber() > roundNumber) {
                continue;
            }

            if (round.getMatchByParticipant(participant) != null && round.getMatchByParticipant(participant).isBye()) {
                byes++;
            }
        }
        return byes;
    }

    public List<Participant> getParticipantPlayedAgainst(Participant participant) {
        return getParticipantPlayedAgainstForAfterRound(participant, rounds.size());
    }

    public List<Participant> getParticipantPlayedAgainstForAfterRound(Participant participant, int roundNumber) {
        List<Participant> opponents = new ArrayList<>();
        log.debug("Getting opponents of participant {}", participant);
        for (Round round : rounds) {
            if (round.getRoundNumber() > roundNumber) {
                continue;
            }
            if (round.getMatchByParticipant(participant) != null) {
                opponents.addAll(round.getMatchByParticipant(participant).getOthers(participant));
            }
        }
        log.debug("Adding opponents {} for participant {}", opponents, participant);
        return opponents;
    }

    public List<Match> getParticipantMatches(Participant participant) {
        return getParticipantMatchesForAfterRound(participant, rounds.size());
    }

    public List<Match> getParticipantMatchesForAfterRound(Participant participant, int roundNumber) {
        List<Match> matches = new ArrayList<>();
        log.debug("Getting matches of participant {}", participant);
        for (Round round : rounds) {
            if (round.getRoundNumber() > roundNumber) {
                continue;
            }
            matches.add(round.getMatchByParticipant(participant));
        }
        return matches;
    }


    // == private methods
    private void updateParticipants() {
        for (Participant participant : this.participants) {
            participant.setScore(getParticipantScore(participant));
            participant.setNumberOfByes(getParticipantByes(participant));
            participant.setPlayedAgainst(getParticipantPlayedAgainst(participant));
            participant.setPlayedMatches(getParticipantMatches(participant));
        }
    }
    private void updateParticipantsForAfterRound(int roundNumber) {
        for (Participant participant : participants) {
            participant.setScore(getParticipantScoreAfterRound(participant, roundNumber));
            participant.setNumberOfByes(getParticipantByesAfterRound(participant, roundNumber));
            participant.setPlayedAgainst(getParticipantPlayedAgainstForAfterRound(participant, roundNumber));
            participant.setPlayedMatches(getParticipantMatchesForAfterRound(participant, roundNumber));
        }
    }
    private void reconcileParticipantCodes() {
        for (int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            participant.setCode(Integer.toString(i+1));
        }
    }

    @Override
    public String toString() {
        return "TournamentImpl{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", participants=" + participants +
                ", startingParticipantScores=" + startingParticipantScores +
                ", startingParticipantByes=" + startingParticipantByes +
                ", rounds=" + rounds +
                ", ruleSet=" + ruleSet +
                '}';
    }


    // == INNER CLASSES
    private class ParticipantManager {
        private List<Participant> participants;
        private Map<Participant, Integer> startingParticipantByes;
        private Map<Participant, Double> startingParticipantScores;

        public boolean addParticipant(Participant participant) {
            if (participants.contains(participant)) {
                log.info("Tournament already contains participant {}", participant);
                return false;
            } else {
                participants.add(participant);
                participant.setCode(Integer.toString(participants.size()));
                startingParticipantScores.put(participant, 0d);
                startingParticipantByes.put(participant, 0);
                participant.setOfTournament(TournamentImpl.this);
                log.info("Adding participant {}", participant);
                return true;
            }
        }

        public Participant removeParticipant(long id) {
            for (Participant participant : this.participants) {
                if (participant.getId() == id) {
                    participant.setOfTournament(null);
                    participants.remove(participant);
                    reconcileParticipantCodes();
                    return participant;
                }
            }
            return null;
        }

        public void setStartingScore(Participant participant, double score) {
            startingParticipantScores.put(participant, score);
        }

        public List<Participant> getParticipantsAfterRound(int roundNumber) {
            updateParticipantsForAfterRound(roundNumber);
            return Collections.unmodifiableList(participants);
        }

        public Double getParticipantScore(Participant participant) {
            log.debug("In getParticipantScore, rounds: {}", rounds);
            return getParticipantScoreAfterRound(participant, rounds.size());
        }

        public Double getParticipantScoreAfterRound(Participant participant, int roundNumber) {
            double score = startingParticipantScores.get(participant);
            for (Round round : rounds) {
                if (round.getRoundNumber() > roundNumber) {
                    continue;
                }
                Match match = round.getMatchByParticipant(participant);
                if (match != null) {
                    MatchResultEnum result = match.getMatchResult(participant);
                    if (result != null) {
                        score += ruleSet.getPoints(result);
                        log.debug("Result for participant {} is {}, score set to {}", participant, result, score);
                    }
                }
            }
            log.debug("Score of participant {} is {} after round {}", participant, score, roundNumber);
            return score;
        }

        public Integer getParticipantByes(Participant participant) {
            return getParticipantByesAfterRound(participant, rounds.size());
        }

        public Integer getParticipantByesAfterRound(Participant participant, int roundNumber) {
            int byes = startingParticipantByes.get(participant);
            for (Round round : rounds) {
                if (round.getRoundNumber() > roundNumber) {
                    continue;
                }

                if (round.getMatchByParticipant(participant) != null
                        && round.getMatchByParticipant(participant).isBye()) {
                    byes++;
                }
            }
            return byes;
        }

        public List<Participant> getParticipantPlayedAgainst(Participant participant) {
            return getParticipantPlayedAgainstForAfterRound(participant, rounds.size());
        }

        public List<Participant> getParticipantPlayedAgainstForAfterRound(Participant participant, int roundNumber) {
            List<Participant> opponents = new ArrayList<>();
            log.debug("Getting opponents of participant {}", participant);
            for (Round round : rounds) {
                if (round.getRoundNumber() > roundNumber) {
                    continue;
                }
                if (round.getMatchByParticipant(participant) != null) {
                    opponents.addAll(round.getMatchByParticipant(participant).getOthers(participant));
                }
            }
            log.debug("Adding opponents {} for participant {}", opponents, participant);
            return opponents;
        }


        // == private methods
        private void updateParticipants() {
            for (Participant participant : this.participants) {
                participant.setScore(getParticipantScore(participant));
                participant.setNumberOfByes(getParticipantByes(participant));
                participant.setPlayedAgainst(getParticipantPlayedAgainst(participant));
            }
        }
        private void updateParticipantsForAfterRound(int roundNumber) {
            for (Participant participant : participants) {
                participant.setScore(getParticipantScoreAfterRound(participant, roundNumber));
                participant.setNumberOfByes(getParticipantByesAfterRound(participant, roundNumber));
                participant.setPlayedAgainst(getParticipantPlayedAgainstForAfterRound(participant, roundNumber));
            }
        }
        private void reconcileParticipantCodes() {
            for (int i = 0; i < participants.size(); i++) {
                Participant participant = participants.get(i);
                participant.setCode(Integer.toString(i+1));
            }
        }
    }

}
