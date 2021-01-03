package com.dreamteam.model;

import com.dreamteam.model.enums.ElevatorStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElevatorA extends Elevator {

    public ElevatorA(Floor floor) {
        super(floor);
    }

    @Override
    public void invoke(User user) throws InterruptedException {
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

    @Override
    protected void pickupUsersOnCurrentFloor() {
        while (true){
        super.pickupUsersOnCurrentFloor();
        log.warn("Current floor of elevatorA " + currentFloor.getNumber());
        log.warn("Current waiting users size elevatorA is:"+waitingUsers.size());
//        System.out.println("\nElevatorr" + currentFloor.getNumber());
        User currentUser;
        if (!currentFloor.getUsersQueueToElevator().get(this).isEmpty()) {
            currentUser = currentFloor.getUsersQueueToElevator().get(this).element();
        } else break;
        log.warn("Current user of elevatorA " + currentUser.getId()+" Start floor:"+currentUser.getStartFloor().getNumber()+" Destination floor"+currentUser.getDestinationFloor().getNumber());
//            System.out.println("\nuserr"+currentUser.getStartFloor().getNumber()+currentUser.getDestinationFloor().getNumber());

        if (currentUser.canUserEnter(this)) {
            //hm
            if (waitingUsers.contains(currentUser))
                waitingUsers.remove(currentUser);
            currentFloor.getUsersQueueToElevator().get(this).poll();
            if (destinations.contains(currentUser.getDestinationFloor())) continue;
            boolean isInserted = false;
            for (int i = 0; i < destinations.size() - 1; ++i) {
                int minFloorNo = Math.min(destinations.get(i).getNumber(), destinations.get(i + 1).getNumber());
                int maxFloorNo = Math.max(destinations.get(i).getNumber(), destinations.get(i + 1).getNumber());
                if (currentUser.getDestinationFloor().getNumber() < maxFloorNo &&
                        currentUser.getDestinationFloor().getNumber() > minFloorNo) {
                    destinations.add(i + 1, currentUser.getDestinationFloor());
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
        if(destinations.size()>1)
    currentDestination =destinations.get(1);
    }
}

