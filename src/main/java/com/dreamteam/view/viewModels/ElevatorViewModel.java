package com.dreamteam.view.viewModels;
import lombok.*;

@AllArgsConstructor
@Getter
public class ElevatorViewModel {
    private int number;
    private int currentActiveUserAmount;
    private int currentCapacity;
    private int maxActiveUserAmount;
    private int maxCapacity;
    private int currentFloor;
}
