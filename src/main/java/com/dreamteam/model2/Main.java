package com.dreamteam.model2;

import com.dreamteam.utils.UserFactory;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        final int FLOOR_COUNT = 10;
        List<Floor> floorList = new ArrayList<>();
        for (int i = 0; i < FLOOR_COUNT; ++i) {
            floorList.add(new Floor(i));
        }

        floorList.get(0).setPreviousFloor(null);
        for (int i = 0, j = 1; j < FLOOR_COUNT; ++i, ++j) {
            floorList.get(j).setPreviousFloor(floorList.get(i));
            floorList.get(i).setNextFloor(floorList.get(j));
        }
        floorList.get(FLOOR_COUNT - 1).setNextFloor(null);

        Elevator elevator = new ElevatorA(floorList.get(0), ElevatorDirection.UP);
//        Elevator elevator2 = new ElevatorA(floorList.get(9));
        List<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(elevator);
//        elevatorList.add(elevator2);
        floorList.forEach(f -> {
            f.initQueues(elevatorList);
        });

        new Thread(() -> {
            try {
                elevator.process();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        new Thread(() -> {
//            try {
//                elevator2.process();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

        TimerTask task = new TimerTask() {
            @SneakyThrows
            public void run() {
                new Thread(() -> {
                    User user = UserFactory.createNewUser(floorList);
                    user.callElevator();
                }).start();
            }
        };
        Timer timer = new Timer();
        long delay = 0L;
        long period = 80L;
        timer.scheduleAtFixedRate(task, delay,period);

    }
}
