package com.dreamteam.model2;

import com.dreamteam.view.ElevatorViewModel;
import com.dreamteam.view.ObservableProperties;
import com.dreamteam.view.UserQueueViewModel;
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
    public static int MAX_USER_COUNT = 10;
    public static int CAPACITY = 1000;
    private static int counter = 0;
    protected int id;
    protected ElevatorStatus status;
    protected List<User> activeUsers;
    protected Queue<User> waitingUsers;
    protected Floor currentFloor;
    protected Floor currentDestination;

    public Elevator(Floor currentFloor) {
        this.currentFloor = currentFloor;
        id = counter++;
        status = ElevatorStatus.FREE;
        activeUsers = new ArrayList<>();
        waitingUsers = new LinkedList<>();
    }

    public synchronized void invoke(User user) {
        if (status == ElevatorStatus.BUSY) {
            waitingUsers.add(user);
            log.info("User " + user.getName() + user.getId() + " added to " + "Elevator" + this.getId() + " waiting list, size: " + waitingUsers.size());
        } else if (status == ElevatorStatus.FREE){
            status = ElevatorStatus.BUSY;
            if (user.getStartFloor() != currentFloor) {
                waitingUsers.add(user);
                log.info("Elevator" + this.getId() + " goes to " + "user " + user.getName() + user.getId());
            }
        }
    }

    public void process() throws InterruptedException {
        while (true) {
            sleep(80);
            deleteUsersWhoExitOnCurrentFloor();
            pickupUsers();
            moveToTheNextFloor();
        }
    }

    protected synchronized void deleteUsersWhoExitOnCurrentFloor() {
        if (activeUsers.removeIf(x -> x.getDestinationFloor() == currentFloor)) {
            log.info("User(s) left Elevator" + this.getId());
        } else {
            log.info("No users left Elevator" + this.getId());
        }
    }

    protected synchronized void pickupUsers() {
        if (currentFloor == null) return;
        while (true) {
            if (!currentFloor.getUsersQueueToElevator().get(this).isEmpty()) {
                User user = currentFloor.getUsersQueueToElevator().get(this).element();
                if (user.canUserEnter(this)) {
                    if (waitingUsers.contains(user))
                        waitingUsers.remove(user);
                    currentFloor.getUsersQueueToElevator().get(this).poll();
                    activeUsers.add(user);
                    log.info("User " + user.getName() + "" + user.getId() +
                            " entered Elevator" + this.getId() +  ", active users: " + activeUsers.size());
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    abstract protected void moveToTheNextFloor();

    public synchronized int getActiveUsersCount() {
        return activeUsers.size();
    }

    public synchronized int getCurrentCapacity() {
        return activeUsers.stream().map(User::getWeight).reduce(0, Integer::sum);
    }

    public synchronized void moveToFloor(Floor floor) {
        this.currentFloor = floor;
        log.info("Elevator" + this.getId() + ", current floor: " +
                (this.currentFloor == null ? "NULL" : this.currentFloor.getNumber()));
    }
}

