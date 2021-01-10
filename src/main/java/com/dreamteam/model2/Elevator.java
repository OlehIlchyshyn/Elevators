package com.dreamteam.model2;

import com.dreamteam.console_colors.ConsoleColors;
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
    public static int MAX_USER_COUNT = 30;
    public static int CAPACITY = 1000;
    private static int counter = 0;
    protected int id;
    protected ElevatorStatus status;
    protected ElevatorDirection direction;
    protected List<User> activeUsers;
    protected Queue<User> waitingUsers;
    protected Floor currentFloor;
    protected Floor currentDestination;

    private PropertyChangeSupport support;

    public Elevator(Floor currentFloor, ElevatorDirection direction, PropertyChangeListener listener) {
        this.currentFloor = currentFloor;
        id = counter++;
        status = ElevatorStatus.FREE;
        activeUsers = new ArrayList<>();
        waitingUsers = new LinkedList<>();

        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
    }

    public synchronized void invoke(User user) {
        if (status == ElevatorStatus.BUSY) {
            waitingUsers.add(user);
            log.info(ConsoleColors.BLUE+"User " + user.getName() + user.getId() + " added to " + "Elevator" + this.getId() + " waiting list, size: " + waitingUsers.size()+ConsoleColors.RESET);
        } else if (status == ElevatorStatus.FREE){
            status = ElevatorStatus.BUSY;
            if (user.getStartFloor() != currentFloor) {
                waitingUsers.add(user);
                log.info(ConsoleColors.YELLOW+"Elevator" + this.getId() + " goes to " + "user " + user.getName() + user.getId()+ConsoleColors.RESET);
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
            log.info(ConsoleColors.BLUE+"User(s) left Elevator" + this.getId()+ConsoleColors.RESET);
        } else {
            log.info(ConsoleColors.BLUE+"No users left Elevator" + this.getId()+ConsoleColors.RESET);
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
                    log.info(ConsoleColors.BLUE+"User " + user.getName() + "" + user.getId() +
                            " entered Elevator" + this.getId() +  ", active users: " + activeUsers.size()+ConsoleColors.RESET);

//                    var userQueueViewModel = new UserQueueViewModel(currentFloor.getNumber(),
//                            id + 1,
//                            waitingUsers.size());
//                    support.firePropertyChange(ObservableProperties.QUEUE_CHANGED.toString(), null, userQueueViewModel);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
    }

    abstract protected void moveToTheNextFloor() throws InterruptedException;

    public synchronized int getActiveUsersCount() {
        return activeUsers.size();
    }

    public synchronized int getCurrentCapacity() {
        return activeUsers.stream().map(User::getWeight).reduce(0, Integer::sum);
    }

    public synchronized void moveToFloor(Floor floor) throws InterruptedException {

        var tempFloorNumber = currentFloor.getNumber();

        while(tempFloorNumber != currentDestination.getNumber()) {
            var elevatorViewModel = new ElevatorViewModel(id + 1,
                    activeUsers.size(),
                    getCurrentCapacity(),
                    Elevator.MAX_USER_COUNT,
                    Elevator.CAPACITY,
                    tempFloorNumber);

            support.firePropertyChange(ObservableProperties.FLOOR_CHANGED.toString(), null, elevatorViewModel);

            switch (direction) {
                case UP:
                    tempFloorNumber++;
                    break;
                case DOWN:
                    tempFloorNumber--;
                    break;
            }

            sleep(50);
        }

        this.currentFloor = floor;

        log.info(ConsoleColors.YELLOW+"Elevator" + this.getId() + ", current floor: " +
                (this.currentFloor == null ? "NULL" : this.currentFloor.getNumber())+ConsoleColors.RESET);
    }
}

