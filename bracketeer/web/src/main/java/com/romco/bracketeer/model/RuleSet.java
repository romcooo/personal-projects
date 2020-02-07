package com.romco.bracketeer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleSet {
    
    public static final int DEFAULT_POINTS_FOR_WIN = 3;
    public static final int DEFAULT_POINTS_FOR_LOSS = 0;
    public static final int DEFAULT_POINTS_FOR_TIE = 1;
    public static final int DEFAULT_BEST_OF = 3;
    
    private int
            pointsForWin,
            pointsForLoss,
            pointsForTie,
            bestOf;
    
    public RuleSet(int pointsForWin, int pointsForLoss, int pointsForTie, int bestOf) {
        this.pointsForWin = pointsForWin;
        this.pointsForLoss = pointsForLoss;
        this.pointsForTie = pointsForTie;
        this.bestOf = bestOf;
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

