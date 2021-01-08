package com.dreamteam;

import com.dreamteam.model.*;
import com.dreamteam.view.MainForm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
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
    }

    private static User createNewUser(List<Floor> floorList){
        Random rand = new Random();
        List<String> names = List.of("Oleh", "Maksym", "Vladyslav", "Valeriia", "Liliia", "Viktoriia", "Anastasiia", "Nazar", "Yaryna", "Illia", "Tetiana");
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
        floorList.get(startFLoorIndex).add(user);
        log.info("New user: " + user.getName() + ", ID: " + user.getId() +
                ", Start floor: " + user.getStartFloor().getNumber() +
                ", Destination floor: " + user.getDestinationFloor().getNumber() +
                " ,Elevator: " + user.getChosenElevator().getClass().getSimpleName() + " " +
                user.getChosenElevator().getId());
        return user;
    }

    public static void timerToCreateNewUser(List<Floor> floors) {
        TimerTask task = new TimerTask() {
            @SneakyThrows
            public void run() {
                new Thread(() -> {
                    try {
                        createNewUser(floors).callElevator();
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

