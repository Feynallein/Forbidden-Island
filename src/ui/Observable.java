package ui;

import java.util.ArrayList;

public abstract class Observable {
    private ArrayList<Observer> observers;

    public Observable(){
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer o){
        observers.add(o);
    }

    public void updateObservers(){
        for(Observer o : observers){
            o.update();
        }
    }
}
