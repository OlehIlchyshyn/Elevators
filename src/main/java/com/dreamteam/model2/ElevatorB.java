package com.dreamteam.model2;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
public class ElevatorB extends Elevator {
    public ElevatorB(Floor currentFloor, ElevatorDirection elevatorDirection) {
        super(currentFloor, elevatorDirection);
    }

    @Override
    protected void moveToTheNextFloor() {
        if (activeUsers.isEmpty()) {
            if (waitingUsers.isEmpty()) {
                status = ElevatorStatus.FREE;
                log.info("No active and waiting users, ElevatorB" + this.id + " is free now");
                return;
            } else {
                // Elevator goes to start floor of the first user in waiting users list
                this.currentDestination = waitingUsers.poll().getStartFloor();
                if (currentDestination.getNumber() >= this.currentFloor.getNumber()) {
                    direction = ElevatorDirection.UP;
                } else {
                    direction = ElevatorDirection.DOWN;
                }
                log.info("ElevatorB" + this.id + " goes to floor " + currentDestination.getNumber() + ", direction: " + direction);
            }
        }
        else {
            int destFloor;
            int tempFloor;
            if (direction == ElevatorDirection.UP) {
                destFloor = activeUsers.stream()
                        .map(User::getDestinationFloor)
                        .map(Floor::getNumber)
                        .filter(x -> x >= this.currentFloor.getNumber())
                        .min(Integer::compareTo)
                        .orElse(-1);
                tempFloor = waitingUsers.stream()
                        .map(User::getStartFloor)
                        .map(Floor::getNumber)
                        .filter(x -> x >= this.currentFloor.getNumber())
                        .min(Integer::compareTo)
                        .orElse(-1);
                if (destFloor != -1 && tempFloor != -1) {
                    destFloor = Math.min(destFloor, tempFloor);
                } else if (destFloor == -1 && tempFloor != -1){
                    destFloor = tempFloor;
                }
            } else {
                destFloor = activeUsers.stream()
                        .map(User::getDestinationFloor)
                        .map(Floor::getNumber)
                        .filter(x -> x < this.currentFloor.getNumber())
                        .max(Integer::compareTo)
                        .orElse(-1);
                tempFloor = waitingUsers.stream()
                        .map(User::getStartFloor)
                        .map(Floor::getNumber)
                        .filter(x -> x < this.currentFloor.getNumber())
                        .max(Integer::compareTo)
                        .orElse(-1);
                destFloor = Math.max(destFloor, tempFloor);
            }
            if (destFloor == -1) {
                destFloor = activeUsers.stream()
                        .map(User::getDestinationFloor)
                        .map(Floor::getNumber)
                        .min(Comparator.comparingInt(x -> Math.abs(x - this.currentFloor.getNumber())))
                        .get();
                if (destFloor >= this.currentFloor.getNumber()) {
                    direction = ElevatorDirection.UP;
                } else {
                    direction = ElevatorDirection.DOWN;
                }
            }
            int finalDestFloor = destFloor;
            User currentUser = activeUsers.stream()
                    .filter(x -> x.getDestinationFloor().getNumber() == finalDestFloor)
                    .findFirst().orElse(null);
            if (currentUser == null) {
                currentUser = waitingUsers.stream()
                        .filter(x -> x.getStartFloor().getNumber() == finalDestFloor)
                        .findFirst().get();
                this.currentDestination = currentUser.getStartFloor();
            } else {
                this.currentDestination = currentUser.getDestinationFloor();
            }
            log.info("ElevatorB" + this.id + " goes to floor " + currentDestination.getNumber() + ", direction: " + direction);
//            if (currentFloor.getNumber() > currentDestination.getNumber()) {
//                if (currentFloor.getPreviousFloor() == null) {
//                    moveToFloor(currentFloor.getNextFloor());
//                } else {
//                    moveToFloor(currentFloor.getPreviousFloor());
//                }
//            } else {
//                if (currentFloor.getNextFloor() == null) {
//                    moveToFloor(currentFloor.getPreviousFloor());
//                } else {
//                    moveToFloor(currentFloor.getNextFloor());
//                }
//            }
        }
        moveToFloor(this.currentDestination);
    }
}
