package com.dreamteam.model2;

import com.dreamteam.Observer;
import com.dreamteam.utils.UserFactory;
import com.dreamteam.view.MainForm;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static int floorAmount;
    public static int elevatorAmount;
    private static Observer observer;

    public static void main(String[] args) {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        var form  = new MainForm();
        form.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExecuteAlgorithm(form);
            }
        });

        form.getSpinnerFloorAmount().setModel(new SpinnerNumberModel(30, 3, 30, 1));
        form.getSpinnerElevatorAmount().setModel(new SpinnerNumberModel(5, 1, 10, 1));

        frame.setContentPane(form.getRootPanel());
        frame.pack();
        frame.setVisible(true);

        observer = new Observer(form.getTable1());
    }

    private static void getNumbers(MainForm form) {
        elevatorAmount = (int)form.getSpinnerElevatorAmount().getValue();
        floorAmount = (int)form.getSpinnerFloorAmount().getValue();
    }

    private static void createTable(MainForm form) {
        DefaultTableModel model = (DefaultTableModel) form.getTable1().getModel();

        getNumbers(form);

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
    }

    private static void ExecuteAlgorithm(MainForm form) {
        createTable(form);

        List<Floor> floorList = new ArrayList<>();
        List<Elevator> elevatorList = new ArrayList<>();

        for (int i = 0; i < floorAmount; ++i) {
            floorList.add(new Floor(i, observer));
        }

        floorList.get(0).setPreviousFloor(null);
        for (int i = 0, j = 1; j < floorAmount; ++i, ++j) {
            floorList.get(j).setPreviousFloor(floorList.get(i));
            floorList.get(i).setNextFloor(floorList.get(j));
        }
        floorList.get(floorAmount - 1).setNextFloor(null);

        for (int i = 0; i < elevatorAmount; ++i) {
            elevatorList.add(new ElevatorA(floorList.get(0), observer, ElevatorDirection.UP));
        }

        floorList.forEach(f -> {
            f.initQueues(elevatorList);
        });

        elevatorList.forEach(el -> {
            new Thread(() -> {
                try {
                    el.process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

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
        long period = 60L;
        timer.scheduleAtFixedRate(task, delay,period);
    }


}
