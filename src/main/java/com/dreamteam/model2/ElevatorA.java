package com.dreamteam.model2;

import com.dreamteam.console_colors.ConsoleColors;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeListener;
import java.util.Comparator;

@Slf4j
public class ElevatorA extends Elevator {
    public ElevatorA(Floor currentFloor, PropertyChangeListener listener, ElevatorDirection elevatorDirection) {
        super(currentFloor, elevatorDirection, listener);
    }

    @Override
    protected synchronized void moveToTheNextFloor() throws InterruptedException {
        if (activeUsers.isEmpty()) {
            if (waitingUsers.isEmpty()) {
                status = ElevatorStatus.FREE;
                log.info(ConsoleColors.YELLOW+"No active and waiting users, elevator is free now"+ConsoleColors.RESET);
                return;
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
        }
        moveToFloor(this.currentDestination);
    }
}

