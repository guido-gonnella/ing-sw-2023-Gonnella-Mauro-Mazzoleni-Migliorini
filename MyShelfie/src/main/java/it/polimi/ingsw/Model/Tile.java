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
    public Type getType(){
        return this.type;
    }

    public int getId() {return id; }
}
