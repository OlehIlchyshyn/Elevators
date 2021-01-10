package com.dreamteam.model2;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private static int userCount = 0;
    private int id;
    private String name;
    private int weight;
    private Floor startFloor;
    private Floor destinationFloor;
    private Elevator chosenElevator;

    public User(String name, int weight, Floor startFloor, Floor destinationFloor) {
        this.id = userCount++;
        this.name = name;
        this.weight = weight;
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

    public void callElevator() {
       chosenElevator.invoke(this);
    }

    public boolean canUserEnter(Elevator elevator) {
        return elevator.getActiveUsersCount() + 1 <= Elevator.MAX_USER_COUNT
                && elevator.getCurrentCapacity() + getWeight() <= Elevator.CAPACITY;
    }
}
