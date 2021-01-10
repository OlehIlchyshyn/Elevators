package com.dreamteam.view.viewModels;
import lombok.*;

@AllArgsConstructor
@Getter
public class UserQueueViewModel {
    private int currentFloor;
    private int elevatorNumber;
    private int usersInQueue;
}
