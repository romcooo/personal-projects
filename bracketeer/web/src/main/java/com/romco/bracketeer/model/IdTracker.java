package com.romco.bracketeer.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class IdTracker {
    private static Map<Class<?>, AtomicInteger> integerMap = new HashMap<>();

//    private IdTracker() {
//        integerMap = new HashMap<>();
//    }
//
//    private static class IdTrackerFactory {
//        private static final IdTracker instance = new IdTracker();
//
//    }
//    public IdTracker getInstance() {
//        return IdTrackerFactory.instance;
//    }

    /**
     *
     * @param o The object the class of which will be used to determine the next ID in sequence.
     *          Make sure to pass the appropriate superclass if IDs should be unique across
     *          child objects.
     *          Example: I have 3 classes, Car, Ferrari extends Car and Ford extends Car.
     *          If this method is called with Ferrari once and with Ford twice, it will return 1
     *          for Ferrari and first 1, then 2 for Ford.
     *          Alternatively, I could choose to call it 3 times with Car, in order to get unique
     *          IDs across all child classes of Car.
     * @return The next unique id for the class represented by object o.
     *
     */
    public static int getNextIdInSequence(Object o) {
        integerMap.putIfAbsent(o.getClass(), new AtomicInteger(1));
        return integerMap.get(o.getClass()).getAndIncrement();
    }
}
