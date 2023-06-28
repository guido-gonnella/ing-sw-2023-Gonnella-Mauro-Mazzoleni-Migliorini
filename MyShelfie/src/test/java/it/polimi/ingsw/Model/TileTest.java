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

        assertSame(tile1.getType(), Type.BOOK);
        assertSame(tile2.getType(), Type.CAT);
        assertSame(tile3.getType(), Type.TROPHY);
        assertSame(tile4.getType(), Type.PLANT);
        assertSame(tile5.getType(), Type.FRAME);
        assertSame(tile6.getType(), Type.GAME);
    }
}
