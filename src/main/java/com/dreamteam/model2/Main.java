package com.dreamteam.model2;

import com.dreamteam.Observer;
import com.dreamteam.utils.UserFactory;
import com.dreamteam.view.FloorRenderer;
import com.dreamteam.view.MainForm;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Main {
    public static int floorAmount;
    public static int elevatorAmount;
    private static Observer observer;
    private static Timer timer;
    private static boolean working;


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

        form.getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StopAlgorithm(form);
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                StopAlgorithm(form);
                System.exit(0);
            }
        });

        //ImageIcon imgThisImg = new ImageIcon("C:\\Users\\Viktoriia\\IdeaProjects\\Elevators\\images.png");

        form.getSpinnerFloorAmount().setModel(new SpinnerNumberModel(30, 3, 50, 1));
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

    private static void clearTable(MainForm form){
        DefaultTableModel model = (DefaultTableModel)form.getTable1().getModel();
        model.setRowCount(0);
        model.setColumnCount(1);
    }

    private static void createTable(MainForm form) {
        DefaultTableModel model = (DefaultTableModel)form.getTable1().getModel();

        getNumbers(form);
        var table = form.getTable1();
        model.addColumn("Floor");

        for(int i = 0; i < elevatorAmount * 2-1; i++) {
            model.addColumn("Elevator #" + i);
        }

        for(int i = 0; i < floorAmount; i++) {
            model.addRow(new Object[elevatorAmount]);
            model.setValueAt(floorAmount - i, i, 0);
        }

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.setRowHeight(form.getTable1().getHeight() / floorAmount);

        FloorRenderer FR = new FloorRenderer();
        FR.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer(FR);


//        ImageIcon imgThisImg = new ImageIcon("C:\\Users\\Viktoriia\\IdeaProjects\\Elevators\\images.png");
//        Label l = new Label();
//        form.getTable1().setValueAt(imgThisImg.getImage(),0,1);

//        created = true;

    }

    private static void StopAlgorithm(MainForm form){
        timer.cancel();
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        threads.forEach(Thread::interrupt);
        working = false;
    }

    private static void ExecuteAlgorithm(MainForm form) {
        if(working)
        {
            JOptionPane.showMessageDialog(null, "Elevators are already working ;)");
            return;
        }

//        DefaultTableModel model = (DefaultTableModel)form.getTable1().getModel();
//        for(int i =0;i<form.getTable1().getRowCount();++i)
//        {
//            model.removeRow(i);
//        }
        clearTable(form);
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

        String strategy = Objects.requireNonNull(form.getComboBoxStrategy().getSelectedItem()).toString();

        Elevator.setCounter(0);
        if(strategy.equals("Strategy A")){
            for (int i = 0; i < elevatorAmount; ++i) {
                elevatorList.add(new ElevatorA(floorList.get(0), observer, ElevatorDirection.UP));
            }

        }

        if(strategy.equals("Strategy B")){
            for (int i = 0; i < elevatorAmount; ++i) {
                elevatorList.add(new ElevatorB(floorList.get(0), observer, ElevatorDirection.UP));
            }
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

        working = true;

        TimerTask task = new TimerTask() {
                    @SneakyThrows
                    public void run() {
                        new Thread(() -> {
                            User user = UserFactory.createNewUser(floorList);
                            user.callElevator();
                        }).start();
                        }
            };
            timer = new Timer();
            long delay = 0L;
            long period = 60L;
            timer.scheduleAtFixedRate(task, delay,period);
        }
}

