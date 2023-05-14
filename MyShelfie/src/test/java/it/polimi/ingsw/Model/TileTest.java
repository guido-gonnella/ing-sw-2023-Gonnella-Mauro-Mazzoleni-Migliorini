package it.polimi.ingsw.Model;

import java.util.*;

import it.polimi.ingsw.Model.Enumeration.Type;
import org.junit.*;
import static org.junit.Assert.*;

public class TileTest {
    @Test
    public void test() {
        Tile tile1 = new Tile(Type.BOOK);
        Tile tile2 = new Tile(Type.CAT);
        Tile tile3 = new Tile(Type.TROPHY);
        Tile tile4 = new Tile(Type.PLANT);
        Tile tile5 = new Tile(Type.FRAME);
        Tile tile6 = new Tile(Type.GAME);

        assertTrue(tile1.getType()==Type.BOOK);
        assertTrue(tile2.getType()==Type.CAT);
        assertTrue(tile3.getType()==Type.TROPHY);
        assertTrue(tile4.getType()==Type.PLANT);
        assertTrue(tile5.getType()==Type.FRAME);
        assertTrue(tile6.getType()==Type.GAME);
    }
}
