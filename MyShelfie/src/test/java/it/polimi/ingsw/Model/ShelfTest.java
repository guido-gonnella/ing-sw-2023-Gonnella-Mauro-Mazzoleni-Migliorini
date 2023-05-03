package it.polimi.ingsw.Model;

import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import it.polimi.ingsw.Model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ShelfTest {

    private Shelf shelf;
    private final int WIDTH = 5;
    private final int HEIGHT = 6;

    @Before
    public void setUp(){
        this.shelf = new Shelf();
        Optional<Tile>[][] grid = shelf.getShelf();

        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                assertTrue(grid[i][j].isEmpty());
            }
        }
    }

    @After
    public void tearDown(){
        this.shelf = null;
    }

    @Test
    public void putTile_right() throws ColumnAlreadyFullException, OutOfShelfException {
        Tile tileC = new Tile(Type.CAT);
        Tile tileT = new Tile(Type.TROPHY);
        Tile tileP = new Tile(Type.PLANT);

        shelf.putTile(tileC,0);
        assertTrue(shelf.getShelf()[HEIGHT-1][0].isPresent());
        if(shelf.getShelf()[HEIGHT-1][0].isPresent())
            assertEquals(tileC, this.shelf.getShelf()[HEIGHT-1][0].get());

        shelf.putTile(tileT,0);
        if(shelf.getShelf()[1][HEIGHT-1].isPresent())
            assertEquals(tileT, this.shelf.getShelf()[1][HEIGHT-1].get() );

        shelf.putTile(tileP, 1);
        if(shelf.getShelf()[0][HEIGHT-2].isPresent())
            assertEquals(tileP,shelf.getShelf()[0][HEIGHT-2].get() );
    }

    @Test(expected = ColumnAlreadyFullException.class)
    public void putTile_ColumnFull() throws ColumnAlreadyFullException, OutOfShelfException {
        Tile tile = new Tile(Type.CAT);
        for(int i=0; i<7; i++){
            shelf.putTile(tile, 0);
        }
    }

    @Test(expected = OutOfShelfException.class)
    public void putTile_OutOfShelf() throws ColumnAlreadyFullException, OutOfShelfException {
        Tile tile = new Tile(Type.CAT);
        shelf.putTile(tile, -1);
        shelf.putTile(tile, 7);
    }

    @Test
    public void getShelf_Test(){
        Optional<Tile>[][] shelf = new Optional[5][6];
        Tile tileC = new Tile(Type.CAT);
        Tile tileP = new Tile(Type.PLANT);
        shelf[0][0] = Optional.of(tileC);
    }
}
