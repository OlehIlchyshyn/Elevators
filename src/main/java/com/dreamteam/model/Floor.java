package com.dreamteam.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Data
@AllArgsConstructor
public class Floor {
    private int number;
    private Map<Elevator, Queue<User>> usersQueueToElevator;

    public Floor() {
        usersQueueToElevator = new HashMap<>();
    }
}
