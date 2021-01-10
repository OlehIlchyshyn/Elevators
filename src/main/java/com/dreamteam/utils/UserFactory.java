package com.dreamteam.utils;

import com.dreamteam.model2.Floor;
import com.dreamteam.model2.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
public class UserFactory {
    private static Random rand = new Random();
    private static List<String> names =
            List.of("Oleh", "Maksym", "Vladyslav", "Valeriia",
                    "Liliia", "Viktoriia", "Anastasiia", "Nazar",
                    "Yaryna", "Illia", "Tetiana");

    public static synchronized User createNewUser(List<Floor> floorList){
        int startFLoorIndex = rand.nextInt(floorList.size());
        int destinationFloorIndex = rand.nextInt(floorList.size());
        if (destinationFloorIndex == startFLoorIndex) {
            if (destinationFloorIndex + 1 == floorList.size()) {
                destinationFloorIndex--;
            } else {
                destinationFloorIndex++;
            }
        }
        var user = new User(names.get(rand.nextInt(names.size())),
                rand.nextInt(200),
                floorList.get(startFLoorIndex),
                floorList.get(destinationFloorIndex));
        floorList.get(startFLoorIndex).addUserToQueue(user);
        log.info("New user: " + user.getName() + ", ID: " + user.getId() +
                ", Start floor: " + user.getStartFloor().getNumber() +
                ", Destination floor: " + user.getDestinationFloor().getNumber() +
                ", " + user.getChosenElevator().getClass().getSimpleName() + "" +
                user.getChosenElevator().getId());
        return user;
    }
}
