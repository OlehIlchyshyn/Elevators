package com.dreamteam.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MainForm {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton startButton;
    private JButton stopButton;

    public JPanel getRootPanel()
    {
        return this.panel1;
    }

    public JTable getTable1()
    {
        return table1;
    }


    public void hello()
    {
        this.textField1.setText("olala");
    }
}

