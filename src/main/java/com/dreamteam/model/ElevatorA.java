package com.dreamteam.model;

import com.dreamteam.model.enums.ElevatorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ElevatorA extends Elevator{

    public ElevatorA(Floor floor) {
        super(floor);
    }

    @Override
    public void invoke(User user) {
        super.invoke(user);
        if (status == ElevatorStatus.BUSY) {
            waitingUsers.add(user);
            return;
        }
        status = ElevatorStatus.BUSY;
        if (user.getStartFloor() != currentFloor) {
            currentDestination = user.getStartFloor();
            destinations.add(currentDestination);
            moveToTheNextFloor();
            return;
        }
        allActionsOnCurrentFloor();
    }
}

