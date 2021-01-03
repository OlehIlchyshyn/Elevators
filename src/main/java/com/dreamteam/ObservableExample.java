package com.dreamteam;

import com.dreamteam.view.ElevatorStatus;
import com.dreamteam.view.MainForm;
import com.dreamteam.view.ElevatorRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ObservableExample {
    public static int floorAmount = 10;
    public static int elevatorAmount = 3;

    public static void main(String[] args) {
        Observer observer = new Observer();
        Observable observable = new Observable();

        observable.addPropertyChangeListener(observer);
        observable.doThings();

        JFrame frame = new JFrame();
        var form  = new MainForm();
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

        ChangeCellColor(form.getTable1().getColumnModel(), 1, 2);
        ChangeCellColor(form.getTable1().getColumnModel(), 2, 5);
        ChangeCellColor(form.getTable1().getColumnModel(), 3, 8);
    }

    public static void ChangeCellColor(TableColumnModel model, int elevatorIndex, int floorIndex) {
        model.getColumn(elevatorIndex)
                .setCellRenderer(new ElevatorRenderer(floorAmount - floorIndex, ElevatorStatus.FULL));
    }
}
