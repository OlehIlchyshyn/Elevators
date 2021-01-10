package com.dreamteam.model2;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Floor {
    private int number;
    private Map<Elevator, Queue<User>> usersQueueToElevator;
    private Floor previousFloor;
    private Floor nextFloor;

    public Floor(int number) {
        this.number = number;
    }

    public void initQueues(List<Elevator> elevators) {
        usersQueueToElevator = new HashMap<>();
        for (var elevator: elevators) {
            if (!usersQueueToElevator.containsKey(elevator)) {
                usersQueueToElevator.put(elevator, new ArrayDeque<>());
            }
        }
    }

    public void addUserToQueue(User user) {
        var elevator= usersQueueToElevator
                .keySet()
                .stream()
                .min(Comparator.comparing(Elevator::getActiveUsersCount))
                .orElseThrow(NoSuchElementException::new);
        usersQueueToElevator.get(elevator).add(user);
        user.setChosenElevator(elevator);
    }
}
