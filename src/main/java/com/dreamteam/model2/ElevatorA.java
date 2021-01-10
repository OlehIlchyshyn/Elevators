package com.dreamteam.model2;

import com.dreamteam.console_colors.ConsoleColors;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@Slf4j
public class ElevatorA extends Elevator {
    public ElevatorA(Floor currentFloor, ElevatorDirection elevatorDirection) {
        super(currentFloor, elevatorDirection);
    }

    @Override
    protected synchronized void moveToTheNextFloor() {
        if (activeUsers.isEmpty()) {
            if (waitingUsers.isEmpty()) {
                status = ElevatorStatus.FREE;
                log.info(ConsoleColors.YELLOW+"No active and waiting users, elevator is free now"+ConsoleColors.RESET);
            } else {
                // Elevator goes to start floor of the first user in waiting users list
                this.currentDestination = waitingUsers.poll().getStartFloor();
                if (currentDestination.getNumber() >= this.currentFloor.getNumber()) {
                    direction = ElevatorDirection.UP;
                } else {
                    direction = ElevatorDirection.DOWN;
                }
                log.info(ConsoleColors.YELLOW+"ElevatorA" + this.id + " goes to floor " + currentDestination.getNumber() + ", direction: " + direction+ConsoleColors.RESET);
            }
        } else {
            // Elevator goes to desired destination of the first user in active users list
            // TODO should be changed to real best destination logic
//            User currentUser = activeUsers.stream().findFirst().get();

            // Now destination is set as closest floor to current in direction of elevator
            int destFloor;
            if (direction == ElevatorDirection.UP) {
                destFloor = activeUsers.stream()
                        .map(User::getDestinationFloor)
                        .map(Floor::getNumber)
                        .filter(x -> x >= this.currentFloor.getNumber())
                        .min(Integer::compareTo)
                        .orElse(-1);
            } else {
                destFloor = activeUsers.stream()
                        .map(User::getDestinationFloor)
                        .map(Floor::getNumber)
                        .filter(x -> x < this.currentFloor.getNumber())
                        .max(Integer::compareTo)
                        .orElse(-1);
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
                    .findFirst().get();
            this.currentDestination = currentUser.getDestinationFloor();
            log.info(ConsoleColors.YELLOW+"ElevatorA" + this.id + " goes to floor " + currentDestination.getNumber() + ", direction: " + direction+ConsoleColors.RESET);
//            log.info("Current user of ElevatorA" + this.id + ": " + currentUser.getId() +
//                    ", Start floor:" + currentUser.getStartFloor().getNumber() +
//                    ", *Destination floor: " + currentUser.getDestinationFloor().getNumber());
        }
        moveToFloor(this.currentDestination);
    }
}

