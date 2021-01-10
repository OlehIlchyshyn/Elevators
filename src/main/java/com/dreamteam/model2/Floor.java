package com.dreamteam.model2;

import com.dreamteam.view.ObservableProperties;
import com.dreamteam.view.UserQueueViewModel;
import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

@Getter
@Setter
public class Floor {
    private int number;
    private Map<Elevator, Queue<User>> usersQueueToElevator;
    private Floor previousFloor;
    private Floor nextFloor;

    private PropertyChangeSupport support;

    public Floor(int number, PropertyChangeListener listener) {
        this.number = number;

        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(listener);
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

        var userQueueViewModel = new UserQueueViewModel(this.number,
                elevator.id + 1,
                elevator.waitingUsers.size());
        support.firePropertyChange(ObservableProperties.QUEUE_CHANGED.toString(), null, userQueueViewModel);
    }
}
