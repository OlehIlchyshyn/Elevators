package com.dreamteam.view;
import lombok.*;

@Getter
public class ElevatorViewModel {
    private int currentActiveUserAmount;
    private int currentCapacity;
    private int maxActiveUserAmount;
    private int maxCapacity;
    private int currentFloor;

    public ElevatorViewModel(int currentActiveUserAmount,
                             int currentCapacity,
                             int maxActiveUserAmount,
                             int maxCapacity,
                             int currentFloor) {
        this.currentActiveUserAmount = currentActiveUserAmount;
        this.currentCapacity = currentCapacity;
        this.maxActiveUserAmount = maxActiveUserAmount;
        this.maxCapacity = maxCapacity;
        this.currentFloor = currentFloor;
    }
}
