package com.romco.bracketeer.domain.tournament;

import com.romco.bracketeer.domain.matcher.TournamentFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Data
public class RuleSet {
    
    public static final double DEFAULT_POINTS_FOR_WIN = 3;
    public static final double DEFAULT_POINTS_FOR_LOSS = 0;
    public static final double DEFAULT_POINTS_FOR_TIE = 1;
    public static final int DEFAULT_BEST_OF = 3;
    public static final TournamentFormat DEFAULT_FORMAT = TournamentFormat.SWISS;
    
    private int id;
    
    private int bestOf;
    private Map<MatchResultEnum, Double> pointMap;
    private TournamentFormat type;

    public RuleSet(double pointsForWin, double pointsForLoss, double pointsForTie, int bestOf, TournamentFormat type) {
        pointMap = new EnumMap<>(MatchResultEnum.class);
        pointMap.put(MatchResultEnum.WIN, pointsForWin);
        pointMap.put(MatchResultEnum.LOSS, pointsForLoss);
        pointMap.put(MatchResultEnum.TIE, pointsForTie);
        pointMap.put(MatchResultEnum.NOT_PLAYED, 0d);

        this.bestOf = bestOf;
        this.type = type;
    }

    public double getPointsForResult(MatchResultEnum result) {
        log.trace("getPoints for {} results in {} points.", result, pointMap.get(result));
        return pointMap.get(result);
    }

    public double getPointsForResult(String result) {
        MatchResultEnum matchResultEnum;
        try {
            matchResultEnum = MatchResultEnum.valueOf(result);
        } catch (IllegalArgumentException e) {
            log.warn("Illegal resultType passed: {}", result);
            return 0d;
        }
        return getPointsForResult(matchResultEnum);
    }

    public boolean setPointsForResult(MatchResultEnum resultType, double pointsForResult) {
        pointMap.put(resultType, pointsForResult);
        log.trace("setPointsForResult for {} to {} points.", resultType, pointMap.get(resultType));
        //TODO check?
        return true;
    }

    public boolean setPointsForResult(String result, double pointsForResult) {
        MatchResultEnum matchResultEnum;
        try {
            matchResultEnum = MatchResultEnum.valueOf(result);
        } catch (IllegalArgumentException e) {
            log.warn("Illegal resultType passed: {}", result);
            return false;
        }
        return setPointsForResult(matchResultEnum, pointsForResult);
    }

    public static RuleSet getDefaultRuleSet() {
        return new RuleSet(
                DEFAULT_POINTS_FOR_WIN,
                DEFAULT_POINTS_FOR_LOSS,
                DEFAULT_POINTS_FOR_TIE,
                DEFAULT_BEST_OF,
                DEFAULT_FORMAT
        );
    }


    
    public static class RuleSetBuilder {
        private final RuleSet ruleSet = getDefaultRuleSet();

        
        public RuleSetBuilder withPointsForWin(double pointsForWin) {
            ruleSet.pointMap.put(MatchResultEnum.WIN, pointsForWin);
            return this;
        }

        public RuleSetBuilder withPointsForLoss(double pointsForLoss) {
            ruleSet.pointMap.put(MatchResultEnum.LOSS, pointsForLoss);
            return this;
        }

        public RuleSetBuilder withPointsForTie(double pointsForTie) {
            ruleSet.pointMap.put(MatchResultEnum.TIE, pointsForTie);
            return this;
        }

        public RuleSetBuilder withBestOf(int bestOf) {
            ruleSet.bestOf = bestOf;
            return this;
        }

        public RuleSetBuilder withType(TournamentFormat type) {
            ruleSet.type = type;
            return this;
        }

        public RuleSet build() {
            return ruleSet;
        }
    }

}

