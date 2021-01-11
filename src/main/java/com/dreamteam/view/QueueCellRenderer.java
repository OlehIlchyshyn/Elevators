package com.dreamteam.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class QueueCellRenderer extends DefaultTableCellRenderer {





    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);

        return c;
    }

}