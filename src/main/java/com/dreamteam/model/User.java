package com.dreamteam.model;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private static int id;
    private String name;
    private int weight;
    private Floor startFloor;
    private Floor destinationFloor;
    private Elevator chosenElevator;

    public User(String name, int weight, Floor startFloor, Floor destinationFloor) {
        this.id = User.id++;
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
//
//    public void chooseElevator() {
//        var elevators=startFloor.getUsersQueueToElevator().values();
//        var minUsersToElevator=1000; //шось мудре тут написати
//        for(var elevator:elevators) {
//            if(elevator.size()<minUsersToElevator) {
//                minUsersToElevator=elevator.size();
//                chosenElevator=startFloor.getUsersQueueToElevator()
//                        .entrySet()
//                        .stream()
//                        .filter(entry -> elevator.equals(entry.getValue()))
//                        .map(Map.Entry::getKey)
//                        .findFirst()
//                        .get();
//            }
//        }
//        System.out.println(elevators);
//    }


}
