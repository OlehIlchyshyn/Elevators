package com.dreamteam.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

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
        if(currentFloor.getNumber()<user.getStartFloor().getNumber()) {
            if (isIntermediateFloorsUp(user.getStartFloor())) {
                currentDestination = user.getStartFloor();
                destinations.add(1, currentDestination);
                moveToTheNextFloor();
                return;
            }
        }
        if(currentFloor.getNumber()<user.getStartFloor().getNumber()) {
            if (isIntermediateFloorsDown(user.getStartFloor())) {
                currentDestination = user.getStartFloor();
                destinations.add(1, currentDestination);
                moveToTheNextFloor();
                return;
            }
        }
        destinations.add(user.getStartFloor());
    }

//
//    private void sortDestinations() {
//        Collections.sort(destinations, Comparator.comparingInt(Floor::getNumber));
//    }
    private boolean isIntermediateFloorsUp(Floor userDestination) {

         if(currentFloor.getNumber()<userDestination.getNumber()&&
                 currentDestination.getNumber()>userDestination.getNumber()) return true;

         return false;
    }
    private boolean isIntermediateFloorsDown(Floor userDestination) {

        if(currentFloor.getNumber()<userDestination.getNumber()&&
                currentDestination.getNumber()>userDestination.getNumber()) return true;

        return false;
    }

    private void addIntermediateFloorsUp() {
        var sortedUserList = waitingUsers.stream().sorted(Comparator.comparingInt(x -> x.getStartFloor().getNumber())).collect(Collectors.toList());
        Collections.reverse(sortedUserList);
        for (var user : sortedUserList) {
            if (isIntermediateFloorsUp(user.getStartFloor())) {
                destinations.add(1,user.getStartFloor());
            }
        }
    }
    private void addIntermediateFloorsDown() {
        var sortedUserList = waitingUsers.stream().sorted(Comparator.comparingInt(x -> x.getStartFloor().getNumber())).collect(Collectors.toList());
        for (var user : sortedUserList) {
            if (isIntermediateFloorsUp(user.getStartFloor())) {
                destinations.add(1,user.getStartFloor());
            }
        }
    }

    @Override
    protected void pickupUsersOnCurrentFloor() {
        super.pickupUsersOnCurrentFloor();
        while (true)
        {
            User currentUser;
            if (!currentFloor.getUsersQueueToElevator().get(this).isEmpty()) {
                currentUser = currentFloor.getUsersQueueToElevator().get(this).element();
            } else break;

            if (currentUser.canUserEnter(this)) {
                //hm
                if(waitingUsers.contains(currentUser))
                    waitingUsers.remove(currentUser);
                currentFloor.getUsersQueueToElevator().get(this).poll();
                if (destinations.contains(currentUser.getDestinationFloor())) continue;
                boolean isInserted = false;
                for (int i = 0; i < destinations.size() - 1; ++i) {
                    int minFloorNo = Math.min(destinations.get(i).getNumber(), destinations.get(i+1).getNumber());
                    int maxFloorNo = Math.max(destinations.get(i).getNumber(), destinations.get(i+1).getNumber());
                    if (currentUser.getDestinationFloor().getNumber() < maxFloorNo &&
                            currentUser.getDestinationFloor().getNumber() > minFloorNo)
                    {
                        destinations.add(i+1, currentUser.getDestinationFloor());
                        isInserted = true;
                        break;
                    }
                }
                //????????
                if (!isInserted) {
                    destinations.add(currentUser.getDestinationFloor());
                }
                activeUsers.add(currentUser);
            } else {
                break;
            }
        }
        if(currentFloor.getNumber()>destinations.get(1).getNumber()) {
            addIntermediateFloorsDown();
        }
        if(currentFloor.getNumber()<destinations.get(1).getNumber()) {
            addIntermediateFloorsUp();
        }


        if(destinations.size()>1)
            currentDestination = destinations.get(1);
    }
}

