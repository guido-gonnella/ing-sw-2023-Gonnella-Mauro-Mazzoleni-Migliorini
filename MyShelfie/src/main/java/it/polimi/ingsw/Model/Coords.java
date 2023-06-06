package it.polimi.ingsw.Model;


import java.io.Serializable;

/**
 * Support class for the method adjacent tiles
 * @author Guido Gonnella
 */
public class Coords implements Serializable {
    public int ROW, COL;

    public Coords(int a, int b){
        this.ROW = a;
        this.COL = b;
    }
}