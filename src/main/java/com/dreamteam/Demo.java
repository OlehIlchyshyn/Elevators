package com.dreamteam;

import com.dreamteam.model.Elevator;
import com.dreamteam.model.ElevatorA;
import com.dreamteam.model.Floor;
import com.dreamteam.model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<Floor> floorList = new ArrayList<>();
        for (int i = 1; i <=9; ++i) {
            floorList.add(new Floor(i, new HashMap<>()));
        }

        Elevator elevator = new ElevatorA(floorList.get(0));

        User user1 = new User(0, "Oleh", 60, floorList.get(4), floorList.get(8), elevator);
        User user2 = new User(1, "Oleh", 60, floorList.get(4), floorList.get(6), elevator);
        User user3 = new User(2, "Oleh", 60, floorList.get(6), floorList.get(7), elevator);
        User user4 = new User(3, "Oleh", 60, floorList.get(6), floorList.get(8), elevator);
        User user5= new User(4, "Oleh", 60, floorList.get(6), floorList.get(2), elevator);
        User user6 = new User(5, "Oleh", 60, floorList.get(1), floorList.get(4), elevator);
        User user7 = new User(6, "Oleh", 60, floorList.get(0), floorList.get(3), elevator);
        User user8 = new User(7, "Oleh", 60, floorList.get(3), floorList.get(5), elevator);

        user1.callElevator();
        //add thread for elevator
        user2.callElevator();
        user3.callElevator();
        user4.callElevator();
        user5.callElevator();
        user6.callElevator();
        user7.callElevator();
        user8.callElevator();



        SwingUtilities.invokeLater(Demo::createAndShowGUI);
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
