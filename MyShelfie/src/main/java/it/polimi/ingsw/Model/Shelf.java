package it.polimi.ingsw.Model;

import java.io.Serializable;

/**
 * Class that describes a player's shelf, with methods to return the infos about it, and the method for inserting a tile
 * in given column
 *
 * @author Guido Gonnella
 */
public class Shelf implements Serializable {

    private final int WIDTH = 5;
    private final int HEIGHT = 6;

    /**
    * The (0,0) coordinate in the shelf is at the top left, and the
    * coordinate (6,5) is at the bottom right.
    * */
    private SerializableOptional<Tile>[][] shelf;


    /**
     * Constructor method of the class
     * Declares the matrix of Optional at the fixed dimensions of 6x5, then initialize all the values at Optional.empty()
     *
     * @author Guido Gonnella
     */
    public Shelf(){
        // Declaration of a 6x5 matrix of Optional
        this.shelf = new SerializableOptional[HEIGHT][WIDTH];

        // Initialization of the matrix
        // Every single spot of the shelf is an empty Optional object
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                this.shelf[i][j] = SerializableOptional.empty();
            }
        }
    }

    /**
     * Returns a copy of the 6x5 matrix shelf
     *
     * @return the copy of the instance attribute <code>shelf</code>
     * @author Guido Gonnella
     * */
    public SerializableOptional<Tile>[][] getShelf(){
        return this.shelf;
    }

    /**
     *  Insert of a single tile at the bottom of a specified column
     *
     * @param tile is the Tile object the player wants to put in their shelf
     * @param col is the column where the player wants to put the tile
     *
     * @author Guido Gonnella
     */
    //il controllo che la colonna esisita e la colonna possa contenere le tiles inserite lo fa il controller
    public void putTile(Tile tile, int col){
        for(int i = HEIGHT-1; i >= 0; i--) {
            if(this.shelf[i][col].isEmpty()){
                this.shelf[i][col] = SerializableOptional.of(tile);
                break;
            }
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

    /**
     * Method that cleans the shelf
     */
    public void cleanShelf(){
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                this.shelf[i][j] = SerializableOptional.empty();
            }
        }
    }

    /**
     * Method to check if the shelf is full.
     * @return true if there aren't any empty spaces int the matrix, false if there is at least one
     * @author Guido Gonnella
     */
    public boolean isFull(){
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < 5; j++){
                if(shelf[i][j].isEmpty()) return false;
            }
        }

        return true;
    }

    /**
     * Method to check how many more tiles you can insert in a column
     *  @param column is the column of the shelf you want to check
     * @return tilesLeft is how many more tiles can fit in that column of the shelf
     * @author Andrea Migliorini
     */
    public int tilesLeftColumn(int column){
        int tilesLeft=0;
        int i=HEIGHT-1;
        for (;i>-1;i--){
            if(shelf[i][column].isEmpty()){
                break;
            }
        }
        for (;i>-1;i--){
            tilesLeft++;
        }
        return(tilesLeft);
    }
}
