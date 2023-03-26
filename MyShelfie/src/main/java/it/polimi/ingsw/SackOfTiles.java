package it.polimi.ingsw;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
public class SackOfTiles {
    private List<Tile> tiles;
    SackOfTiles () {
        tiles = new ArrayList<Tile>(); //creates a new list of tiles with all of the 132 tiles tiles inside it
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
    public Tile getTile() throws SackIsEmpty() { //gets a random tile from the ordered sack of tiles
        if (tiles.size()== 0) throw new SackIsEmpty();
        else {
            Tile temp;
            int a = ThreadLocalRandom.current().nextInt(tiles.size());
            temp = remove(a);
            return (temp);
        }
    }

}
