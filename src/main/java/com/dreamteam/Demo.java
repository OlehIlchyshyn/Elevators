package com.dreamteam;

import com.dreamteam.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

@Slf4j

//З новим роком!!!!
//┈┈┈┈┈┈┈┈☆┈┈┈┈┈┈┈┈
//┈┈┈┈┈┈┈╱┊╲┈┈┈┈┈┈┈
//┈┈┈┈┈┈╱┊┊┊╲┈┈┈┈┈┈
//┈┈┈┈┈╱┊┊┊┊┊╲┈┈┈┈┈
//┈┈┈┈┈▔▔▔▉▔▔▔┈┈┈┈┈


public class Demo {
    public static void main(String[] args) {
        List<Floor> floorList = new ArrayList<>();
        for (int i = 0; i <=10; ++i) {
            floorList.add(new Floor(i));
        }

        Elevator elevator = new ElevatorA(floorList.get(0));
        Elevator elevator2 = new ElevatorB(floorList.get(0));
        List<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(elevator);
        elevatorList.add(elevator2);
        floorList.forEach(f -> f.initQueues(elevatorList));

        timerToCreateNewUser(floorList);

//
//
//        User user1 = newUser(floorList);
//        User user2 = newUser(floorList);
//        User user3 = newUser(floorList);
//        User user4 = newUser(floorList);
//        User user5 = newUser(floorList);
//        User user6 = newUser(floorList);
//        User user7 = newUser(floorList);
//        User user8 = newUser(floorList);
//        User user9 = newUser(floorList);
//        User user2 = new User(1, "Oleh", 60, floorList.get(4), floorList.get(6));
//        User user3 = new User(2, "Oleh", 60, floorList.get(6), floorList.get(7));
//        User user4 = new User(3, "Oleh", 60, floorList.get(6), floorList.get(8));
//        User user5= new User(4, "Oleh", 60, floorList.get(6), floorList.get(2));
//        User user6 = new User(5, "Oleh", 60, floorList.get(1), floorList.get(4));
//        User user7 = new User(6, "Oleh", 60, floorList.get(0), floorList.get(3));
//        User user8 = new User(7, "Oleh", 60, floorList.get(3), floorList.get(5));
//        ;
//        add thread for elevator
//        user2.callElevator();
//        user3.callElevator();
//        user4.callElevator();
//        user5.callElevator();
//        user6.callElevator();
//        user7.callElevator();
//        user8.callElevator();
//
//
//
//        SwingUtilities.invokeLater(Demo::createAndShowGUI);
    }


    private static User newUser (List<Floor> floorList) throws InterruptedException {
        int randomStartFloor=1+(int) (Math.random()*floorList.size()-1);
        int randomName=1+(int) (Math.random()*10);
        int randomWeight=40+(int) (Math.random()*100);
        int randomDestinationFloor=1+(int) (Math.random()*floorList.size()-1);
        List<String> names = List.of("Oleh", "Maksym", "Vladyslav", "Valeriia", "Liliia", "Viktoriia", "Anastasiia", "Nazar", "Yaryna", "Illia", "Tetiana");
        String name = names.get(randomName);
        var user = new User(name,  randomWeight, floorList.get(randomStartFloor), floorList.get(randomDestinationFloor));
        floorList.get(randomStartFloor).add(user);
        log.warn("Created new user "+name+" User ID: "+user.getId()+" User start floor is: "+user.getStartFloor().getNumber()+" User destination floor is: "+user.getDestinationFloor().getNumber());

        threadCreator(user);
        user.callElevator();
        return user;
    }

    public static void threadCreator(User user)
    {
        userThread thread = new userThread();
        log.warn("New thread created for user with ID "+user.getId()+". Thread ID :" + thread.getId());
        thread.run();
    }

    public static void timerToCreateNewUser(List<Floor> floors) {
        TimerTask task = new TimerTask() {
            @SneakyThrows
            public void run() {
                newUser(floors);
            }
        };
        Timer timer = new Timer();

        long delay = 2L;
        long period = 10L;
        timer.scheduleAtFixedRate(task, delay,period);
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
class userThread extends Thread {
    public void run(){
        System.out.println("Thread is working...");
    }
}

