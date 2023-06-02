package it.polimi.ingsw.Model;
import it.polimi.ingsw.Enumeration.Type;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that contains all the tiles to insert on the board
 *
 * @author Andrea Migliorini
 */
public class SackOfTiles {
    private ArrayList<Tile> tiles;
    public SackOfTiles() {

        tiles = new ArrayList<Tile>(); //creates a new list of tiles with all the 132 tiles of the 6 types inside it
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.CAT,(i%3)+1));}
        for (int i=0; i<22;i++) {
            tiles.add( new Tile(Type.FRAME,(i%3)+1));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.GAME,(i%3)+1));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.PLANT,(i%3)+1));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.TROPHY,(i%3)+1));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(Type.BOOK,(i%3)+1));}

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
    public Tile getRandomTile() { //gets a random tile from the ordered sack of tiles
        if (this.tiles.isEmpty()) return null;
        else {
            Random rand = new Random();
            Tile temp;
            int a = rand.nextInt(tiles.size());
            temp = tiles.remove(a);
            return (temp);
        }
    }

}
