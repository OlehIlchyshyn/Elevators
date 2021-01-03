package com.dreamteam;

import com.dreamteam.view.MainForm;
import com.dreamteam.view.ElevatorRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ObservableExample {
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
        model.addColumn("Column 1");
        model.addColumn("Column 2");
        model.addColumn("Column 3");
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});


        form.getTable1().getColumnModel().getColumn(1).setCellRenderer(new ElevatorRenderer(6));
        form.getTable1().getColumnModel().getColumn(0).setCellRenderer(new ElevatorRenderer(3));
    }
}
