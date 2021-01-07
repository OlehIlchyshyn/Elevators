package com.dreamteam.view;
import lombok.*;

@Getter
public class UserQueueViewModel {
    private int currentFloor;
    private int elevatorNumber;
    private int usersInQueue;

    public UserQueueViewModel(int currentFloor, int elevatorNumber, int usersInQueue) {
        this.currentFloor = currentFloor;
        this.elevatorNumber = elevatorNumber;
        this.usersInQueue = usersInQueue;
    }
}
