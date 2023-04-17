package it.polimi.ingsw;
import exceptions.SackEmptyException;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Class that contains all of the tiles to insert on the board
 *
 * @author Andrea Migliorini
 */
public class SackOfTiles {
    private ArrayList<Tile> tiles;
    public SackOfTiles () {

        tiles = new ArrayList<Tile>(); //creates a new list of tiles with all of the 132 tiles of the 6 types inside it
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.CAT));}
        for (int i=0; i<22;i++) {
            tiles.add( new Tile(Type.FRAME));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.GAME));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.PLANT));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.TROPHY));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.BOOK));}

    }

    /**
     * This method return the list of all the tiles still available to be drawn
     *
     * @return list of tiles left
     * @author Pierantonio Mauro
     */
    public ArrayList<Tile> getLeftTiles(){
        return this.tiles;
    }

    /**
     * @return a random tile of the one still available
     */
    public Tile getRandomTile() throws SackEmptyException { //gets a random tile from the ordered sack of tiles
        if (this.tiles.isEmpty()) throw new SackEmptyException();
        else {
            Random rand = new Random();
            Tile temp;
            int a = rand.nextInt(tiles.size());;
            temp = tiles.remove(a);
            return (temp);
        }
    }

}
