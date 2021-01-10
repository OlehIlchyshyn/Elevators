package com.dreamteam;

import com.dreamteam.model2.Floor;
import com.dreamteam.model2.Main;
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
            ElevatorStatus status = ElevatorStatus.FREE;

            if(elevator.getCurrentActiveUserAmount() > 0) {
                status = ElevatorStatus.WORK;
            }

            if(elevator.getCurrentActiveUserAmount() == elevator.getMaxActiveUserAmount() ||
               elevator.getMaxCapacity() - elevator.getCurrentCapacity() <= 40) {
                status = ElevatorStatus.FULL;
            }

            ChangeCellColor(table.getColumnModel(), elevator.getNumber(), elevator.getCurrentFloor(), status);
        }

        if(evt.getPropertyName().equals(ObservableProperties.QUEUE_CHANGED.toString())) {
            var userQueue = (UserQueueViewModel)evt.getNewValue();

            StringBuilder cellText = new StringBuilder();

//            for(int i = 0; i < userQueue.getUsersInQueue(); i++) {
//                cellText.append("♀");
//            }

            cellText.append(userQueue.getUsersInQueue());

//            try {
                table.setValueAt(cellText.toString(),
                        Main.floorAmount - userQueue.getCurrentFloor() - 1,
                        userQueue.getElevatorNumber() * 2 - 1);
//            } catch(Exception ex) {
////                JOptionPane.showMessageDialog(null, userQueue.getCurrentFloor());
//            }
        }

        table.repaint();
    }

    public static void ChangeCellColor(TableColumnModel model, int elevatorIndex, int floorIndex, ElevatorStatus status) {
        model.getColumn(elevatorIndex * 2)
                .setCellRenderer(new ElevatorRenderer(Main.floorAmount - floorIndex, status));
    }
//
//    switch(Variables) {
//        case :
//
//    }
}
