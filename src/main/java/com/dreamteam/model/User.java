package com.dreamteam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private int weight;
    private Floor startFloor;
    private Floor destinationFloor;
    private Elevator chosenElevator;

    /**
     *
     * @return whether lift is coming or not(user will be waiting until elevator will be free)
     */
    public void callElevator() {
        chosenElevator.invoke(this);
    }

    protected boolean canUserEnter(Elevator elevator) {
        return elevator.getCurrentUserCount() + 1 <= Elevator.maxUserCount && elevator.getCurrentCapacity() + getWeight() <= Elevator.capacity;
    }


}
