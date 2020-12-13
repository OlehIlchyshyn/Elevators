package com.dreamteam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private int weight;
    private Floor startFloor;
    private Floor destinationFloor;
    private Elevator chosenElevator;

    public User(int id, String name, int weight, Floor startFloor, Floor destinationFloor) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
    }

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
