package com.dreamteam;

import com.dreamteam.model.*;
import com.dreamteam.view.MainForm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    public static int floorAmount = 10;
    public static int elevatorAmount = 1;
    private static Observer observer;

    public static void main(String[] args) throws InterruptedException {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        var form  = new MainForm();
        form.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExecuteAlgorithm();
            }
        });

        frame.setContentPane(form.getRootPanel());
        frame.pack();
        frame.setVisible(true);

        DefaultTableModel model = (DefaultTableModel) form.getTable1().getModel();

        model.addColumn("Floor");

        for(int i = 0; i < elevatorAmount; i++) {
            model.addColumn("Elevator #" + i);
        }

        for(int i = 0; i < floorAmount; i++) {
            model.addRow(new Object[elevatorAmount]);
            model.setValueAt(floorAmount - i, i, 0);
        }

        form.getTable1().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        form.getTable1().getColumnModel().getColumn(0).setMaxWidth(50);

        observer = new Observer(form.getTable1());
    }

    private static void ExecuteAlgorithm() {
        List<Floor> floorList = new ArrayList<>();
        for (int i = 0; i <= floorAmount; ++i) {
            floorList.add(new Floor(i));
        }

        Elevator elevator = new ElevatorA(floorList.get(0));
//        Elevator elevator2 = new ElevatorB(floorList.get(0));
        List<Elevator> elevatorList = new ArrayList<>();
        elevatorList.add(elevator);

        elevatorList.forEach((Elevator el) -> {
            el.addPropertyChangeListener(observer);
        });

//        elevatorList.add(elevator2);
        floorList.forEach(f -> {
            f.initQueues(elevatorList);
            f.addPropertyChangeListener(observer);
        });

        timerToCreateNewUser(floorList);
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
                new Thread(() -> {
                    try {
                        newUser(floors);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        };
        Timer timer = new Timer();

        long delay = 50L;
        long period = 1000L;
        timer.scheduleAtFixedRate(task, delay,period);
    }


}
class userThread extends Thread {
    public void run(){
        System.out.println("Thread is working...");
    }
}

