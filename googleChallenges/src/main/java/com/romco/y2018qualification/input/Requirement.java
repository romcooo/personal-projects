package com.romco.y2018qualification.input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Requirement {
    int rows;
    int cols;
    int numberOfVehicles;
    int numberOfRides;
    int perRideBonusForStartOnTime;
    int numberOfStepsInSimulation;
    List<InputRide> rides;
    
    public Requirement(int rows, int cols, int numberOfVehicles, int numberOfRides, int perRideBonusForStartOnTime, int numberOfStepsInSimulation, List<InputRide> rides) {
        this.rows = rows;
        this.cols = cols;
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfRides = numberOfRides;
        this.perRideBonusForStartOnTime = perRideBonusForStartOnTime;
        this.numberOfStepsInSimulation = numberOfStepsInSimulation;
        this.rides = rides;
    }
    
    @Override
    public String toString() {
        return "Requirement{" +
                "rows=" + rows +
                ", cols=" + cols +
                ", numberOfVehicles=" + numberOfVehicles +
                ", numberOfRides=" + numberOfRides +
                ", perRideBonusForStartOnTime=" + perRideBonusForStartOnTime +
                ", numberOfStepsInSimulation=" + numberOfStepsInSimulation +
                ", rides='" + rides + '\'' +
                '}';
    }
    

    public void solve() {
        // 1. remove impossible rides
        for (InputRide ride : rides) {
            if (ride.getDistance() > ride.getLatestFinish() - ride.getEarliestStart()) {
                rides.remove(ride);
                System.out.println("Removing impossible ride: " + ride);
            }
        }
    
        //sort by whatever is higher -> earliest start or distance from 0,0;
        System.out.println("Unsorted rides:\n" + rides);
        rides.sort(Comparator.comparingInt(r -> Math.max(r.getStartX() + r.getStartY(), r.getEarliestStart())));
//        rides.sort(Comparator.comparingInt(r -> (r.getStartX() + r.getStartY())));
//        rides.sort(Comparator.comparingInt(InputRide::getEarliestStart));
        System.out.println("Sorted rides:\n" + rides);
    
        // 2. create pools?
        for (int v = 0; v < numberOfVehicles; v++) {
            List<Integer> rideIds = new ArrayList<>();
    
            //add first ride
            InputRide firstRide = rides.get(0);
            int distanceToFirstRide = firstRide.getStartX() + firstRide.getStartY();
            int remainingSteps = this.numberOfStepsInSimulation - firstRide.getDistance() + distanceToFirstRide;
            int currentX = firstRide.getEndX();
            int currentY = firstRide.getEndY();
            rideIds.add(rides.remove(0).getId());
            int currentStep = rides.get(0).getDistance();
            
            int tolerance = 0;
            while (remainingSteps > 0) {
                int finalTolerance = tolerance;
                int finalCurrentStep = currentStep;
                List<InputRide> remainingEligibleRides = rides.stream()
                                                              .filter((r) -> Math.abs(r.getStartX() - currentX) + Math.abs(r.getStartY() - currentY) + r.getDistance() <= remainingSteps)
                                                              .filter((r) -> r.getEarliestStart() <= (
                                                                              finalCurrentStep
                                                                              + finalTolerance
                                                                              + Math.abs(r.getStartX() - currentX)
                                                                              + Math.abs(r.getStartY() - currentY) ))
                                                              .sorted((r1, r2) -> {
                                                                  return
                                                                          Math.abs(r1.getStartX() - currentX) + Math.abs(r1.getStartY() - currentY) + r1.getDistance()
                                                                          - Math.abs(r2.getStartX() - currentX) + Math.abs(r2.getStartY() - currentY) + r2.getDistance();
                                                              })
                                                              .collect(Collectors.toList());
                if (!remainingEligibleRides.isEmpty()) {
                    InputRide ride = remainingEligibleRides.get(0);
                    rideIds.add(ride.getId());
                    rides.remove(ride);
                }
                tolerance++;
            }
        }
    }
    
    
    
}
