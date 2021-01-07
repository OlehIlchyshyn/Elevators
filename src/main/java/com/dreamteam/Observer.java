package com.dreamteam;

import com.dreamteam.view.*;

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
        if(evt.getPropertyName().equals(ObservableProperties.FLOOR_CHANGED.toString())) {
            var elevator = (ElevatorViewModel)evt.getNewValue();
            ElevatorStatus status = ElevatorStatus.WORK;

            if(elevator.getCurrentActiveUserAmount() == 0) {
                status = ElevatorStatus.FREE;
            }

            if(elevator.getCurrentActiveUserAmount() == elevator.getMaxActiveUserAmount() ||
               elevator.getMaxCapacity() - elevator.getCurrentCapacity() <= 40) {
                status = ElevatorStatus.FULL;
            }

            ChangeCellColor(table.getColumnModel(), 1, elevator.getCurrentFloor(), status);
        }

        if(evt.getPropertyName().equals(ObservableProperties.QUEUE_CHANGED.toString())) {
            var userQueue = (UserQueueViewModel)evt.getNewValue();

            String cellText = "";

            for(int i = 0; i < userQueue.getUsersInQueue(); i++) {
                cellText += "\uD83D\uDC36";
            }

            cellText += "\uD83D\uDC36";

            table.setValueAt(cellText,
                    Demo.floorAmount - userQueue.getCurrentFloor(),
                    userQueue.getElevatorNumber());
        }

        table.repaint();
    }

    public static void ChangeCellColor(TableColumnModel model, int elevatorIndex, int floorIndex, ElevatorStatus status) {
        model.getColumn(elevatorIndex)
                .setCellRenderer(new ElevatorRenderer(Demo.floorAmount - floorIndex, status));
    }
//
//    switch(Variables) {
//        case :
//
//    }
}
