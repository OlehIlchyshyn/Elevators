package com.dreamteam.model;

import com.dreamteam.model.enums.ElevatorStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Data
public abstract class Elevator {
    public static int maxUserCount = 10;
    public static int capacity = 1000;

    private int id;
    private ElevatorStatus status;
    private List<User> activeUsers;
    private Queue<User> waitingUsers;
    private List<Floor> destinations;
    private Floor currentFloor;
    private Floor currentDestination;

    public abstract void moveToNextFloor();

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
    }

    private void deleteUserWhoExitOnCurrentFloor() {
        activeUsers.removeIf(x -> x.getDestinationFloor() == currentFloor);
    }

    private void pickupUsersOnCurrentFloor() {
        while (true)
        {
            var currentUser = currentFloor.getUsersQueueToElevator().get(this).element();
            if (currentUser.canUserEnter(this)) {
                waitingUsers.remove(currentUser);
                currentFloor.getUsersQueueToElevator().get(this).poll();
                destinations.stream().map(Floor::getNumber).min(Integer::compare);
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
                if (!isInserted) {
                    destinations.add(currentUser.getDestinationFloor());
                }
                activeUsers.add(currentUser);
            } else {
                break;
            }
        }
        currentDestination = destinations.get(1);
    }

    protected void moveToFloor(Floor floor) {
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

    public void invoke(User user) {
        if (status == ElevatorStatus.FREE) {
            status = ElevatorStatus.BUSY;
            currentDestination = user.getStartFloor();
            destinations.add(currentDestination);
            if (user.getStartFloor() == currentFloor) {
                allActionsOnCurrentFloor();
            } else {
                moveToFloor(user.getStartFloor());
            }
        } else {
            waitingUsers.add(user);
        }
    }
}
