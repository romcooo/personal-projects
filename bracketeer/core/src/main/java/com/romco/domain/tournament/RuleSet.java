package com.romco.domain.tournament;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class RuleSet {
    
    public static final double DEFAULT_POINTS_FOR_WIN = 3;
    public static final double DEFAULT_POINTS_FOR_LOSS = 0;
    public static final double DEFAULT_POINTS_FOR_TIE = 1;
    public static final int DEFAULT_BEST_OF = 3;
    
    private int id;
    
    private int bestOf;
    
    private Map<MatchResultEnum, Double> pointMap;

    public RuleSet(double pointsForWin, double pointsForLoss, double pointsForTie, int bestOf) {
        pointMap = new HashMap<>(3);
        pointMap.put(MatchResultEnum.WIN, pointsForWin);
        pointMap.put(MatchResultEnum.LOSS, pointsForLoss);
        pointMap.put(MatchResultEnum.TIE, pointsForTie);
        
        this.bestOf = bestOf;
    }

    public double getPoints(MatchResultEnum result) {
//        log.debug("getPoints for {} results in {} points.", result, pointMap.get(result));
        return pointMap.get(result);
    }

    public static RuleSet getDefaultRuleSet() {
        return new RuleSet(
                DEFAULT_POINTS_FOR_WIN,
                DEFAULT_POINTS_FOR_LOSS,
                DEFAULT_POINTS_FOR_TIE,
                DEFAULT_BEST_OF
        );
    }

}

