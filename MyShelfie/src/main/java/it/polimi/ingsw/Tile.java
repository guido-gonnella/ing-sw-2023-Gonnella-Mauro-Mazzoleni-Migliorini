package it.polimi.ingsw;

/**
 * Class that represent the single tiles that can be placed on the
 * board and on the players shelves
 *
 * @author Pierantonio Mauro
 */
public class Tile {

    Type type;

    Tile(Type type){
        this.type = type;
    }
    public Type getType(){
        return this.type;
    }
}
