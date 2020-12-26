package com.dreamteam.model;

import com.dreamteam.model.enums.ElevatorStatus;

import java.util.Collections;
import java.util.Comparator;

public class ElevatorB extends Elevator {
    public ElevatorB(Floor floor) {
        super(floor);
    }


    @Override
    public void invoke(User user) {
        super.invoke(user);
        if (user.getStartFloor() == currentFloor) {
            allActionsOnCurrentFloor();
            return;
        }
        waitingUsers.add(user);
        if(isIntermediateFloors(user.getStartFloor())) {
            currentDestination = user.getStartFloor();
            destinations.add(0, currentDestination);
            moveToTheNextFloor();
            return;
        }
        destinations.add(user.getStartFloor());
    }

//
//    private void sortDestinations() {
//        Collections.sort(destinations, Comparator.comparingInt(Floor::getNumber));
//    }
    private boolean isIntermediateFloors (Floor userDestination) {

         if(currentFloor.getNumber()<userDestination.getNumber()&&
          currentDestination.getNumber()>userDestination.getNumber()) return true;
         return false;
    }


}

