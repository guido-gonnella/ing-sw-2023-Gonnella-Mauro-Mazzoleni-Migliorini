package it.polimi.ingsw.Observer;

import javax.swing.text.View;
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ViewObservable     {
    protected static List<ViewObserver> observers = new ArrayList<ViewObserver>();

    /**
     * Add an observer to the list
     * @param obs observer to be added
     */
    public void addObserver(ViewObserver obs){
        this.observers.add(obs);
    }

    /**
     * Remove an observer from the list
     * @param obs observer to be removed
     */
    public void removeObserver(ViewObserver obs){
        this.observers.remove(obs);
    }

    /**
     * Update all the observers in the list
     * @param lambda consumer that accept an observer. <br>
     *               (obs) -> obs.onSelectTile(...)
     */
    public static void notifyObservers(Consumer<ViewObserver> lambda){
        for(ViewObserver obs : observers){
            lambda.accept(obs);
        }
    }
}
