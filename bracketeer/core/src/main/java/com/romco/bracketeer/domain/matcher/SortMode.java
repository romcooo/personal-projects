package com.romco.bracketeer.domain.matcher;

import com.romco.bracketeer.domain.participant.Participant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum SortMode {
    SHUFFLE_THEN_SORT {
        public List<Participant> sort(List<Participant> before) {
            List<Participant> after = new ArrayList<>(before);
            Collections.shuffle(after);
            after.sort(Comparator.comparingDouble(Participant::getScore).reversed());
            return after;
        }
    },
    KEEP_SEED_ORDER_AND_SORT {
        @Override
        List<Participant> sort(List<Participant> before) {
            List<Participant> after = new ArrayList<>(before);
            after.sort(Comparator.comparingDouble(Participant::getScore).reversed());
            return after;
        }
    };
    abstract List<Participant> sort(List<Participant> before);
}
