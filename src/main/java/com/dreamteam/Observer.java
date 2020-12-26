package com.dreamteam;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Observer implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Observer: " + evt.getPropertyName() + " " + evt.getNewValue());
    }
}
