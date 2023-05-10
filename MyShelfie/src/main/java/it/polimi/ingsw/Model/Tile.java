package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Enumeration.Type;

import java.io.Serializable;

/**
 * Class that represent the single tiles that can be placed on the
 * board and on the players shelves
 *
 * @author Pierantonio Mauro
 */
public class Tile implements Serializable {

    private Type type;

    public Tile(Type type){
        this.type = type;
    }
    public Type getType(){
        return this.type;
    }
}
