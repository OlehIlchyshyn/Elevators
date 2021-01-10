package com.dreamteam.model2;

import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
public class ElevatorB extends Elevator {
    public ElevatorB(Floor currentFloor, PropertyChangeListener listener) {
        super(currentFloor, listener);
    }

    @Override
    protected void moveToTheNextFloor() {
        // Elevator goes to next closest floor where active user wants to OR waiting user is waiting
        // TODO should be changed to real best destination logic

        if (activeUsers.isEmpty()) {
            if (waitingUsers.isEmpty()) {
                status = ElevatorStatus.FREE;
                log.info("No active and waiting users, ElevatorB" + this.id + " is free now");
                return;
            } else {
                // Elevator goes to start floor of the first user in waiting users list
                this.currentDestination = waitingUsers.poll().getStartFloor();
                log.info("ElevatorB" + this.id + " goes to floor " + currentDestination.getNumber());
            }
        } else {

            // Now destination is set as fairest floor to current
            int destFloor = activeUsers.stream()
                    .map(User::getDestinationFloor)
                    .map(Floor::getNumber)
                    .max(Comparator.comparingInt(x -> Math.abs(x - this.currentFloor.getNumber())))
                    .get();
            User currentUser = activeUsers.stream()
                    .filter(x -> x.getDestinationFloor().getNumber() == destFloor)
                    .findFirst().get();
            this.currentDestination = currentUser.getDestinationFloor();
        }
        if (currentFloor.getNumber() > currentDestination.getNumber()) {
            if (currentFloor.getPreviousFloor() == null) {
                moveToFloor(currentFloor.getNextFloor());
            } else {
                moveToFloor(currentFloor.getPreviousFloor());
            }
        } else {
            if (currentFloor.getNextFloor() == null) {
                moveToFloor(currentFloor.getPreviousFloor());
            } else {
                moveToFloor(currentFloor.getNextFloor());
            }
        }
    }
}

