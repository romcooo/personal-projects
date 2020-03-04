package com.romco.domain.tournament;

import com.romco.domain.matcher.Matcher;
import com.romco.domain.matcher.TournamentFormat;
import com.romco.domain.participant.Participant;
import com.romco.domain.util.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class TournamentImpl implements Tournament {
    private static int idCounter;
    private long id;
    private String code; // business id
    private String name;
    private List<Participant> participants;
    private Map<Participant, Double> startingParticipantScores;
    private Map<Participant, Integer> startingParticipantByes;
    private List<Round> rounds;
    private TournamentFormat type;
    private RuleSet ruleSet;

    private TournamentImpl() {
        this.id = idCounter++;
        this.code = CodeGenerator.getCode();
        this.name = "";
        this.participants = new ArrayList<>();
        this.startingParticipantScores = new HashMap<>();
        this.startingParticipantByes = new HashMap<>();
        this.rounds = new LinkedList<>();
        this.ruleSet = RuleSet.getDefaultRuleSet();
    }

    /**
     * Creates a TournamentImpl with a RuleSet.getDefaultRuleSet()
     * @param type - TournamentType (Swiss, Round Robin, Single Elim, Double Elim)
     */
    public TournamentImpl(TournamentFormat type) {
        this();
        this.type = type;
    }
    
    public TournamentImpl(String code, String name, TournamentFormat type) {
        this();
        this.code = code;
        this.name = name;
        this.type = type;
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
        this.type = type;
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
    
    @Override
    public long getId() {
        return id;
    }
    
    @Override
    public void setId(long id) {
        this.id = id;
    }
    
    @Override
    public String getCode() {
        return code;
    }
    
    @Override
    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public TournamentFormat getType() {
        return type;
    }
    
    @Override
    public void setType(TournamentFormat type) {
        this.type = type;
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

    public void setStartingScore(Participant participant, double score) {
        startingParticipantScores.put(participant, score);
    }

    @Override
    public Round generateNextRound() {
        log.info("Unsorted participants: {}", participants);

        return generateRound(rounds.size()+1);

//        updateParticipants();
//
//        Matcher matcher = type.buildMatcher();
//        // don't let anyone touch participants outside of this class
//        Round round = matcher.generateRound(Collections.unmodifiableList(participants));
//        round.setRoundNumber(rounds.size()+1);
//        rounds.add(round);
//
//        return round;
    }

    @Override
    public Round generateRound(int roundNumber) {
        updateParticipantsForAfterRound(roundNumber - 1);

        Matcher matcher = type.buildMatcher();
        Round round = matcher.generateRound(Collections.unmodifiableList(participants));
        round.setRoundNumber(rounds.size()+1);
        rounds.add(round);

        return round;
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
//        return rounds.get(n);
    }

    @Override
    public List<Round> getRounds() {
        return rounds;
    }
    
    public Participant getParticipant(int n) {
        return participants.get(n);
    }

    private void reconcileParticipantCodes() {
        for (int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            participant.setCode(Integer.toString(i+1));
        }
    }

    @Override
    public boolean setMatchResult(int roundNumber,
                                  Participant participant,
                                  int gamesWon,
                                  int gamesLost) {
        getRound(roundNumber).getMatch(participant)
                             .setMatchScore(participant,
                                            gamesWon,
                                            gamesLost);

        return false;
    }

    @Override
    public boolean setMatchResult(int roundNumber,
                                  String participantCode,
                                  int gamesWon,
                                  int gamesLost) {
        for (Participant participant : participants) {
            if (participant.getCode().equals(participantCode)) {
                setMatchResult(roundNumber, participant, gamesWon, gamesLost);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setMatchResult(int roundNumber,
                                  String participantCode,
                                  int gamesWon) {

            for (Participant participant : participants) {
                if (participant.getCode().equals(participantCode)) {
                    getRound(roundNumber).getMatch(participant)
                                         .setMatchScore(participant, gamesWon);
                    return true;
                }
            }
            return false;
    }

    @Override
    public List<Participant> getParticipants() {
        updateParticipants();
        return Collections.unmodifiableList(participants);
    }

    @Override
    public List<Participant> getParticipantsForAfterRound(int roundNumber) {
        updateParticipantsForAfterRound(roundNumber);
        return Collections.unmodifiableList(participants);
    }

    public Double getParticipantScore(Participant participant) {
//        double score = startingParticipantScores.get(participant);
//        for (Round round : rounds) {
//            MatchResultEnum result = round
//                    .getMatch(participant)
//                    .getMatchResult(participant)
//                    .getResult();
//            if (result != null) {
//                score += ruleSet.getPoints(result);
//                log.debug("Result for participant {} is {}, score set to {}", participant, result, score);
//            }
//        }
//        log.debug("Score of participant {} is {}", participant, score);
//        return score;
        log.debug("In getParticipantScore, rounds: {}", rounds);
        return getParticipantScoreAfterRound(participant, rounds.size());
    }

    public Double getParticipantScoreAfterRound(Participant participant, int roundNumber) {
        double score = startingParticipantScores.get(participant);
        for (Round round : rounds) {
            if (round.getRoundNumber() > roundNumber) {
                continue;
            }
            MatchResultEnum result = round
                    .getMatch(participant)
                    .getMatchResult(participant);
            if (result != null) {
                score += ruleSet.getPoints(result);
                log.debug("Result for participant {} is {}, score set to {}", participant, result, score);
            }
        }
        log.debug("Score of participant {} is {} after round {}", participant, score, roundNumber);
        return score;
    }
    
    public Integer getParticipantByes(Participant participant) {
        return getParticipantByesAfterRound(participant, rounds.size());
        //        int byes = startingParticipantByes.get(participant);
//        for (Round round : rounds) {
//            if (round.getMatch(participant) != null && round.getMatch(participant).isBye()) {
//                byes++;
//            }
//        }
//        return byes;
    }

    public Integer getParticipantByesAfterRound(Participant participant, int roundNumber) {
        int byes = startingParticipantByes.get(participant);
        for (Round round : rounds) {
            if (round.getRoundNumber() > roundNumber) {
                continue;
            }

            if (round.getMatch(participant) != null && round.getMatch(participant).isBye()) {
                byes++;
            }
        }
        return byes;
    }
    
    public List<Participant> getParticipantPlayedAgainst(Participant participant) {
        return getParticipantPlayedAgainstForAfterRound(participant, rounds.size());
//        List<Participant> opponents = new ArrayList<>();
//        log.debug("Getting opponents of participant {}", participant);
//        for (Round round : rounds) {
//            if (round.getMatch(participant) != null) {
//                opponents.addAll(round.getMatch(participant).getOthers(participant));
//            }
//        }
//        log.debug("Adding opponents {} for participant {}", opponents, participant);
//        return opponents;
    }

    public List<Participant> getParticipantPlayedAgainstForAfterRound(Participant participant, int roundNumber) {
        List<Participant> opponents = new ArrayList<>();
        log.debug("Getting opponents of participant {}", participant);
        for (Round round : rounds) {
            if (round.getRoundNumber() > roundNumber) {
                continue;
            }
            if (round.getMatch(participant) != null) {
                opponents.addAll(round.getMatch(participant).getOthers(participant));
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
                ", type=" + type +
                ", ruleSet=" + ruleSet +
                '}';
    }
}
