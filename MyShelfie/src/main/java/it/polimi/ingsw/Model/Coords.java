package it.polimi.ingsw.Model;


import java.io.Serializable;

/**
 * Support class for the method adjacent tiles
 * @author Guido Gonnella
 */
public class Coords implements Serializable {
    public int x, y;
    public Coords(int a, int b){
        this.x = a;
        this.y = b;
    }
}