package com.dreamteam.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusColumnCellRenderer extends DefaultTableCellRenderer {

    private int _row;
    private int _col;
    public StatusColumnCellRenderer(int row, int col)
    {
        this._row = row;
        this._col = col;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        //Cells are by default rendered as a JLabel.
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, _row, _col);

        //Get the status for the current row.

        l.setBackground(Color.GREEN);

        // l.setBackground(Color.RED);


        //Return the JLabel which renders the cell.
        return l;
    }
}