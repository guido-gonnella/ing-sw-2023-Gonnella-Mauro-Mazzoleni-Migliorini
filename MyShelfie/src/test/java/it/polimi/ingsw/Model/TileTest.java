package it.polimi.ingsw.Model;

import it.polimi.ingsw.Enumeration.Type;
import org.junit.*;
import static org.junit.Assert.*;

public class TileTest {
    @Test
    public void createTiles_assureTheyHaveSameTypeFromCreation() {
        Tile tile1 = new Tile(Type.BOOK,1);
        Tile tile2 = new Tile(Type.CAT,1);
        Tile tile3 = new Tile(Type.TROPHY,1);
        Tile tile4 = new Tile(Type.PLANT,1);
        Tile tile5 = new Tile(Type.FRAME,1);
        Tile tile6 = new Tile(Type.GAME,1);

        assertTrue(tile1.getType()==Type.BOOK);
        assertTrue(tile2.getType()==Type.CAT);
        assertTrue(tile3.getType()==Type.TROPHY);
        assertTrue(tile4.getType()==Type.PLANT);
        assertTrue(tile5.getType()==Type.FRAME);
        assertTrue(tile6.getType()==Type.GAME);
    }
}
