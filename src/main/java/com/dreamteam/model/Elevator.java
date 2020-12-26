package com.dreamteam.model;

import com.dreamteam.model.enums.ElevatorStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
@Setter
public abstract class Elevator {
    public static int maxUserCount = 10;
    public static int capacity = 1000;

    protected int id;
    protected ElevatorStatus status;
    protected List<User> activeUsers;
    protected Queue<User> waitingUsers;
    protected List<Floor> destinations;
    protected Floor currentFloor;
    protected Floor currentDestination;


    public Elevator(Floor floor) {
        currentFloor = floor;
        status = ElevatorStatus.FREE;
        activeUsers = new ArrayList<>();
        waitingUsers = new LinkedList<>();
        destinations = new ArrayList<>();
        destinations.add(currentFloor);
    }

    protected int getCurrentUserCount() {
        return activeUsers.size();
    }
    protected int getCurrentCapacity() {
        return activeUsers.stream().map(User::getWeight).reduce(0, Integer::sum);
    }

    public void allActionsOnCurrentFloor() {
        deleteUserWhoExitOnCurrentFloor();
        pickupUsersOnCurrentFloor();
        if(destinations.size()==1) {
            status = ElevatorStatus.FREE;
            if(waitingUsers.size()!=0) {
                takeWaitingUsers();
            }
            return;
        }
        moveToTheNextFloor();
    }

    protected void takeWaitingUsers() {
        invoke(waitingUsers.poll());
    }

    public void moveToTheNextFloor() {
      moveToFloor();
      allActionsOnCurrentFloor();
    }

    protected void deleteUserWhoExitOnCurrentFloor() {
        activeUsers.removeIf(x -> x.getDestinationFloor() == currentFloor);
    }

    protected void pickupUsersOnCurrentFloor() {
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
        if(destinations.size()>1)
            currentDestination = destinations.get(1);
    }

    protected void moveToFloor() {
        if (currentDestination == null) {
            this.status = ElevatorStatus.FREE;
            return;
        }
        destinations.remove(0);
        currentFloor = destinations.get(0);
        if (destinations.size() < 2) {
            currentDestination = null;
        }
    }

    public  void  invoke(User user) {
    }
}

