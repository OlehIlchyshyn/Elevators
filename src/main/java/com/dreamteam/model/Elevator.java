package com.dreamteam.model;

import com.dreamteam.model.enums.ElevatorStatus;
import com.dreamteam.view.ObservableProperties;
import com.dreamteam.view.viewModels.UserQueueViewModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.lang.Thread.sleep;

@Getter
@Setter
@Slf4j

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

    private PropertyChangeSupport support;

    public Elevator(Floor floor) {
        support = new PropertyChangeSupport(this);

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

    public void allActionsOnCurrentFloor() throws InterruptedException {
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

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    protected void takeWaitingUsers() throws InterruptedException {
        invoke(waitingUsers.poll());

        var userQueueViewModel = new UserQueueViewModel(this.currentFloor.getNumber(),
                this.id + 1,
                this.waitingUsers.size());
        support.firePropertyChange(ObservableProperties.QUEUE_CHANGED.toString(), null, userQueueViewModel);
    }

    public void moveToTheNextFloor() throws InterruptedException {
      moveToFloor();
      allActionsOnCurrentFloor();
    }

    protected void deleteUserWhoExitOnCurrentFloor() {
        activeUsers.removeIf(x -> x.getDestinationFloor() == currentFloor);
    }

    protected void pickupUsersOnCurrentFloor() {


    }

    protected void moveToFloor() throws InterruptedException {
        sleep(100);
        if (currentDestination == null) {
            this.status = ElevatorStatus.FREE;
            return;
        }
        destinations.remove(0);

        currentFloor = destinations.get(0);

//        var elevatorViewModel = new ElevatorViewModel(this.activeUsers.size(),
//                this.getCurrentCapacity(),
//                Elevator.maxUserCount,
//                Elevator.capacity,
//                this.currentFloor.getNumber());
//
//        support.firePropertyChange(ObservableProperties.FLOOR_CHANGED.toString(), null, elevatorViewModel);

        if (destinations.size() < 2) {
            currentDestination = null;
            this.status = ElevatorStatus.FREE;
        }
    }

    public  void  invoke(User user) throws InterruptedException {
    }
}

