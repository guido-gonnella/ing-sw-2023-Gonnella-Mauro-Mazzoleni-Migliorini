package it.polimi.ingsw.Model;

import it.polimi.ingsw.Enumeration.Type;

import java.io.Serializable;

/**
 * basic element of the private objective to assign points, it has the coordinates and type of the virtual tile of the shelf
 *
 * @author Andrea Migliorini
 */
public class ElementObjective implements Serializable {
    private final int ROW;
    private final int COL;
    private final Type type;
    public ElementObjective(int ROW, int COL, Type kind) // simple constructor method
    {
        this.type=kind;
        this.ROW =ROW;
        this.COL =COL;
    }

    /**
     * basic getters
     */
    public int getROW() {
        return ROW;
    }

    public int getCOL() {
        return COL;
    }

    public Type getType() {
        return type;
    }
}
