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

    private RuleSet ruleSet;

    private List<Round> rounds;

    private List<Participant> participants;
    private Map<Participant, Integer> startingParticipantByes;

    private ParticipantManager participantManager;

    // == CONSTRUCTORS

    public TournamentImpl() {
        this.id = idCounter++;
        this.code = CodeGenerator.getCode();
        this.name = "";
        this.ruleSet = RuleSet.getDefaultRuleSet();

        this.participants = new ArrayList<>();
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
        ruleSet.setType(type);
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
        participant.giveAdditionalPoints(score);
    }

    @Override
    public Round generateNextRound() {
        log.info("Unsorted participants: {}", participants);
        return generateRound(rounds.size()+1);
    }

    @Override
    public Round generateRound(int roundNumber) {
        log.info("In generateRound for round {}", roundNumber);
        Matcher matcher = ruleSet.getType().buildMatcher();

        Round round = matcher.generateRound(Collections.unmodifiableList(participants),
                                            SortMode.SHUFFLE_THEN_SORT,
                                            roundNumber);
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
        Optional<Round> round = rounds.stream().filter(r -> r.getRoundNumber() == roundNumber).findFirst();
        if (round.isEmpty()) {
            log.info("round of number {} doesn't exist", roundNumber);
            return null;
        }
        return round.get();
    }

    //TODO remove this
    @Override
    public List<MatchResult> setMatchResult(int roundNumber,
                                  Participant participant,
                                  int gamesWon,
                                  int gamesLost) {

        return getRound(roundNumber).getMatchByParticipant(participant)
                                    .setMatchScore(participant,
                                                   gamesWon,
                                                   gamesLost);
    }


    public List<MatchResult> setMatchResult(int roundNumber,
                                            int matchNumber,
                                            Map<String, Integer> gamesWonByParticipantCodes) {
        Map <Participant, Integer> gamesWonByParticipants = new HashMap<>();
        for (Map.Entry<String, Integer> entry : gamesWonByParticipantCodes.entrySet()) {
            for (Participant participant: participants) {
                if (participant.getCode().equals(entry.getKey())) {
                    gamesWonByParticipants.put(participant, entry.getValue());
                }
            }
        }
        return setMatchResultByParticipant(roundNumber, matchNumber, gamesWonByParticipants);
    }

    public List<MatchResult> setMatchResultByParticipant(int roundNumber,
                                                         int matchNumber,
                                                         Map<Participant, Integer> gamesWonByParticipants) {
        Round r = rounds.stream()
                        .filter(round -> round.getRoundNumber() == roundNumber)
                        .findAny().orElse(null);
        if (r == null) {
            log.warn("no round of number {}", roundNumber);
            return Collections.emptyList();
        }
        return r.setMatchResult(matchNumber, gamesWonByParticipants);
    }

    @Override
    public List<Participant> getParticipants() {
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
                ", startingParticipantByes=" + startingParticipantByes +
                ", rounds=" + rounds +
                ", ruleSet=" + ruleSet +
                '}';
    }


    // == INNER CLASSES
    private class ParticipantManager {
        private List<Participant> participants;
        private Map<Participant, Integer> startingParticipantByes;

        public boolean addParticipant(Participant participant) {
            if (participants.contains(participant)) {
                log.info("Tournament already contains participant {}", participant);
                return false;
            } else {
                participants.add(participant);
                participant.setCode(Integer.toString(participants.size()));
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
            participant.giveAdditionalPoints(score);
        }

        private void reconcileParticipantCodes() {
            for (var i = 0; i < participants.size(); i++) {
                Participant participant = participants.get(i);
                participant.setCode(Integer.toString(i+1));
            }
        }
    }

}
