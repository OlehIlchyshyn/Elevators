package com.dreamteam;

import com.dreamteam.model.Elevator;
import com.dreamteam.model.ElevatorA;
import com.dreamteam.model.Floor;
import com.dreamteam.model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<Floor> floorList = new ArrayList<>();
        for (int i = 0; i <=3; ++i) {
            floorList.add(new Floor(i));
        }

        Elevator elevator = new ElevatorA(floorList.get(0));
        List<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(elevator);
        floorList.forEach(f -> f.initQueues(elevatorList));


        User user1 = newUser(floorList);
        User user2 = newUser(floorList);
        User user3 = newUser(floorList);
        User user4 = newUser(floorList);
        User user5 = newUser(floorList);
        User user6 = newUser(floorList);
        User user7 = newUser(floorList);
        User user8 = newUser(floorList);
        User user9 = newUser(floorList);
//        User user2 = new User(1, "Oleh", 60, floorList.get(4), floorList.get(6));
//        User user3 = new User(2, "Oleh", 60, floorList.get(6), floorList.get(7));
//        User user4 = new User(3, "Oleh", 60, floorList.get(6), floorList.get(8));
//        User user5= new User(4, "Oleh", 60, floorList.get(6), floorList.get(2));
//        User user6 = new User(5, "Oleh", 60, floorList.get(1), floorList.get(4));
//        User user7 = new User(6, "Oleh", 60, floorList.get(0), floorList.get(3));
//        User user8 = new User(7, "Oleh", 60, floorList.get(3), floorList.get(5));
//        ;
        //add thread for elevator
//        user2.callElevator();
//        user3.callElevator();
//        user4.callElevator();
//        user5.callElevator();
//        user6.callElevator();
//        user7.callElevator();
//        user8.callElevator();



//        SwingUtilities.invokeLater(Demo::createAndShowGUI);
    }
    private static User newUser (List<Floor> floorList) {
        int randomStartFloor=1+(int) (Math.random()*floorList.size()-1);
        int randomDestinationFloor=1+(int) (Math.random()*floorList.size()-1);
        var user = new User("Oleh", 60, floorList.get(randomStartFloor), floorList.get(randomDestinationFloor));
        floorList.get(randomStartFloor).add(user);
        user.callElevator();

        return user;
    }
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
