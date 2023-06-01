package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Network.Message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Class use for notifying the class that implements the observer interface<br>
 * @author Guido Gonnella
 */
public abstract class Observable {
    private static final List<Observer> observers = new ArrayList<Observer>();

    /**
     * Add to the list an observer
     * @param obs the observer to be added
     */
    public void addObserver(Observer obs){
        this.observers.add(obs);
    }

    /**
     * Remove the observer passed as the attribute
     * @param obs the observer to be removed
     */
    public void removeObserver(Observer obs){
        this.observers.remove(obs);
    }

    /**
     * Notify all the added observers with the passed message
     * @param msg the message
     */
  /*  public static void notifyAllObservers(Message msg){
        for(Observer o : observers){
            observers.
        }
    }*/
}
