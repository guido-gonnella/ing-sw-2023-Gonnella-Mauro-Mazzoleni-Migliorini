package it.polimi.ingsw;

import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import java.util.Optional;

/**
 * Class that describes a player's shelf, with methods to return the infos about it, and the method for inserting a tile
 * in given column
 *
 * @author Guido Gonnella
 */
public class Shelf {

    private final int WIDTH = 5;
    private final int HEIGHT = 6;

    /**
    * The (0,0) coordinate in the shelf is at the top left, and the
    * coordinate (6,5) is at the bottom right.
    * */
    private Optional<Tile>[][] shelf;


    /**
     * Constructor method of the class
     * Declare the matrix of Optional at the fixed dimensions of 6x5, then initialize all the values at Optional.empty()
     *
     * @author Guido Gonnella
     */
    public Shelf(){
        // Declaration of a 6x5 matrix of Optional
        this.shelf = new Optional[HEIGHT][WIDTH];

        // Initialization of the matrix
        // Every single spot of the shelf is an empty Optional object
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                shelf[i][j] = Optional.empty();
            }
        }
    }

    /**
     * Returns a copy of the 6x5 matrix shelf
     *
     * @return the copy of the instance attribute <code>shelf</code>
     * @author Guido Gonnella
     * */
    public Optional<Tile>[][] getShelf(){
        return this.shelf;
    }

    /**
     *  Insert of a single tile at the bottom of a specified column
     *
     * @param tile is the Tile object the player wants to put in their shelf
     * @param col is the column where the player wants to put the tile
     *
     * @exception ColumnAlreadyFullException if <code>shelf[0][col].isPresent()</code> is true
     * @exception OutOfShelfException if <code>col >= WIDTH && col < 0</code>
     *
     * @author Guido Gonnella
     */
    public void putTile(Tile tile, int col) throws ColumnAlreadyFullException, OutOfShelfException {
        if(0 <= col && col < WIDTH){
            if(shelf[0][col].isPresent()) throw new ColumnAlreadyFullException("Column " + col + " is full\n");
            else{
                for(int i = HEIGHT-1; i >= 0; i--){
                    if(shelf[i][col].isEmpty()){
                        shelf[i-1][col] = Optional.of(tile);
                        break;
                    }
                }
            }
        }else{
            throw new OutOfShelfException("Out of column range\n");
        }
    }

    /**
     * @return Return the {@link #HEIGHT Height} attribute
     * @author Guido Gonnella
     */
    public int getHEIGHT(){ return HEIGHT;}

    /**
     * @return Return the {@link #WIDTH Width} attribute
     * @author Guido Gonnella
     */
    public int getWIDTH(){ return WIDTH;}
}
