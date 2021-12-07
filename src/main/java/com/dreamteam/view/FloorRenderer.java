package com.dreamteam.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class FloorRenderer extends DefaultTableCellRenderer {


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);
        return c;
    }

}