package it.polimi.ingsw.Model;

import it.polimi.ingsw.Enumeration.Type;

import java.io.Serializable;

/**
 * Class that represent the single tiles that can be placed on the
 * board and on the players shelves
 *
 * @author Pierantonio Mauro
 */
public class Tile implements Serializable {

    private Type type;
    private int id;
    public Tile(Type type, int id){
        this.type = type;
        this.id=id;
    }

    /**
     * @return the {@link Type} of the tile.
     */
    public Type getType(){
        return this.type;
    }

    /**
     * @return the id of the tile, which is used in the view.
     */
    public int getId() {return id; }
}
