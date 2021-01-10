package com.dreamteam.model2;

import com.dreamteam.console_colors.ConsoleColors;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Getter
@Setter
@Slf4j
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

    public synchronized void addUserToQueue(User user) {
        var smallestQueue = usersQueueToElevator
                .values()
                .stream()
                .min(Comparator.comparing(Queue::size))
                .orElseThrow(NoSuchElementException::new);
        var elevator = usersQueueToElevator
                .entrySet()
                .stream()
                .filter(entry -> smallestQueue.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow(NoSuchElementException::new);
        smallestQueue.add(user);
        user.setChosenElevator(elevator);
        log.info(ConsoleColors.PURPLE+"Queue at floor" + this.getNumber()+ ", elevator" + elevator.getId() + ", queue size: " + this.getUsersQueueToElevator().get(elevator).size() + ConsoleColors.RESET);
    }
}
