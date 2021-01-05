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
//        Observer observer = new Observer();
//        Observer observer2 = new Observer();
//        Observable observable = new Observable();
//
//        observable.addPropertyChangeListener(observer);
//        observable.addPropertyChangeListener(observer2);
//        observable.doThings();


//
//        ChangeCellColor(form.getTable1().getColumnModel(), 1, 2);
//        ChangeCellColor(form.getTable1().getColumnModel(), 2, 5);
//        ChangeCellColor(form.getTable1().getColumnModel(), 3, 8);
//
//        form.getTable1().setValueAt("\uD83D\uDC68", 1, 1);
//        form.getTable1().setValueAt("\uD83D\uDC7F", 2, 2);
    }

    public static void ChangeCellColor(TableColumnModel model, int elevatorIndex, int floorIndex) {
        model.getColumn(elevatorIndex)
                .setCellRenderer(new ElevatorRenderer(floorAmount - floorIndex, ElevatorStatus.FULL));
    }
}
