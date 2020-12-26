package com.dreamteam;

public class ObservableExample {
    public static void main(String[] args) {
        Observer observer = new Observer();
        Observable observable = new Observable();

        observable.addPropertyChangeListener(observer);
        observable.doThings();
    }
}
