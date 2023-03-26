package it.polimi.ingsw;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Class that contains all of the tiles to insert on the board
 *
 * @author Andrea Migliorini
 */
public class SackOfTiles {
    private List<Tile> tiles;
    public SackOfTiles () {
        tiles = new ArrayList<Tile>(); //creates a new list of tiles with all of the 132 tiles of the 6 types inside it
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(CAT));}
        for (int i=0; i<22;i++) {
            tiles.add( new Tile(FRAME));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(GAME));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(PLANT));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(TROPHY));}
        for (int i=0; i<22;i++) {
            tiles.add(new Tile(BOOK));}
    }
    public Tile getTile() throws SackEmptyException { //gets a random tile from the ordered sack of tiles
        if (tiles.size()== 0) throw new SackEmptyException();
        else {
            Tile temp;
            int a = ThreadLocalRandom.current().nextInt(tiles.size());
            temp = tiles.remove(a);
            return (temp);
        }
    }

}
