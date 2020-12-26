package com.dreamteam;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Data
@Slf4j
public class Observable {
    private String message;

    private PropertyChangeSupport support;

    public Observable() {
        message = "Zero";
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void doThings() {
        log.info("Do things");
        support.firePropertyChange("message", null, "Do things");
        this.message = "Do things";
    }
}
