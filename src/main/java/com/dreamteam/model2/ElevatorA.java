package com.dreamteam.model2;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@Slf4j
public class ElevatorA extends Elevator {
    public ElevatorA(Floor currentFloor) {
        super(currentFloor);
    }

    @Override
    protected synchronized void moveToTheNextFloor() {
        if (activeUsers.isEmpty()) {
            if (waitingUsers.isEmpty()) {
                status = ElevatorStatus.FREE;
                log.info("No active and waiting users, elevator is free now");
            } else {
                // Elevator goes to start floor of the first user in waiting users list
                this.currentDestination = waitingUsers.poll().getStartFloor();
                log.info("ElevatorA" + this.id + " goes to floor " + currentDestination.getNumber());
            }
        } else {
            // Elevator goes to desired destination of the first user in active users list
            // TODO should be changed to real best destination logic
//            User currentUser = activeUsers.stream().findFirst().get();

            // Now destination is set as closest floor to current
            int destFloor = activeUsers.stream()
                    .map(User::getDestinationFloor)
                    .map(Floor::getNumber)
                    .min(Comparator.comparingInt(x -> Math.abs(x - this.currentFloor.getNumber())))
                    .get();
            User currentUser = activeUsers.stream()
                    .filter(x -> x.getDestinationFloor().getNumber() == destFloor)
                    .findFirst().get();
            this.currentDestination = currentUser.getDestinationFloor();
            log.info("ElevatorA" + this.id + " goes to floor " + currentDestination.getNumber());
//            log.info("Current user of ElevatorA" + this.id + ": " + currentUser.getId() +
//                    ", Start floor:" + currentUser.getStartFloor().getNumber() +
//                    ", *Destination floor: " + currentUser.getDestinationFloor().getNumber());
        }
        moveToFloor(this.currentDestination);
    }
}

