package com.dreamteam;

import com.dreamteam.view.ElevatorRenderer;
import com.dreamteam.view.ElevatorStatus;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Observer implements PropertyChangeListener {
    public JTable table;

    public Observer(JTable table) {
        this.table = table;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        System.out.println("Observer: " + evt.getPropertyName() + " " + evt.getNewValue());


        if(evt.getPropertyName().equals("currentFloor")) {
            ChangeCellColor(table.getColumnModel(), 1, (int)evt.getNewValue());
        }
    }

    public static void ChangeCellColor(TableColumnModel model, int elevatorIndex, int floorIndex) {
        model.getColumn(elevatorIndex)
                .setCellRenderer(new ElevatorRenderer(Demo.floorAmount - floorIndex, ElevatorStatus.FULL));
    }
//
//    switch(Variables) {
//        case :
//
//    }
}
