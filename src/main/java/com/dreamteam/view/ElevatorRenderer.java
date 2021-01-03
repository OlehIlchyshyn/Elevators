package com.dreamteam.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ElevatorRenderer extends DefaultTableCellRenderer {
    private int _row;
    public ElevatorRenderer(int row)
    {
        this._row = row;
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Only for specific cell
        if (row == _row ) {
            //c.setForeground(/*special foreground color*/);
            c.setBackground(Color.BLUE);
        }
        else
        {
            c.setBackground(Color.WHITE);
        }
        return c;
    }
}