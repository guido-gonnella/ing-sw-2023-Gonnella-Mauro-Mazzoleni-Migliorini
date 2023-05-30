package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Enumeration.Type;
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

        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                assertTrue(this.shelf.getShelf()[i][j].isEmpty());
            }
        }
    }

    @After
    public void tearDown(){
        this.shelf = null;
    }

    @Test
    public void putTile_right() {
        Tile tileC = new Tile(Type.CAT,1);
        Tile tileT = new Tile(Type.TROPHY,1);
        Tile tileP = new Tile(Type.PLANT,1);

        shelf.putTile(tileC,0);
        assertTrue(shelf.getShelf()[HEIGHT-1][0].isPresent());
        if(shelf.getShelf()[HEIGHT-1][0].isPresent())
            assertEquals(tileC, this.shelf.getShelf()[HEIGHT-1][0].get());

        shelf.putTile(tileT,1);
        assertTrue(shelf.getShelf()[HEIGHT-1][1].isPresent());
        if(shelf.getShelf()[HEIGHT-1][1].isPresent())
            assertEquals(tileT, this.shelf.getShelf()[HEIGHT-1][1].get() );

        shelf.putTile(tileP, 0);
        assertTrue(shelf.getShelf()[HEIGHT-2][0].isPresent());
        if(shelf.getShelf()[HEIGHT-2][0].isPresent())
            assertEquals(tileP,shelf.getShelf()[HEIGHT-2][0].get() );

        this.shelf.cleanShelf();
    }

    @Test
    public void getShelf_Test() {
        Optional<Tile>[][] shelf = new Optional[HEIGHT][WIDTH];
        for(int i = 0; i < HEIGHT; i++){
            for(int j = 0; j < WIDTH; j++){
                shelf[i][j] = Optional.empty();
            }
        }

        Tile tileC = new Tile(Type.CAT,1);
        Tile tileP = new Tile(Type.PLANT,1);
        shelf[HEIGHT-1][0] = Optional.of(tileC);
        shelf[HEIGHT-2][0] = Optional.of(tileC);
        shelf[HEIGHT-1][WIDTH-1] = Optional.of(tileP);
        shelf[HEIGHT-2][WIDTH-1] = Optional.of(tileP);
        this.shelf.putTile(tileC, 0);
        this.shelf.putTile(tileC, 0);
        this.shelf.putTile(tileP, WIDTH-1);
        this.shelf.putTile(tileP, WIDTH-1);
        assertEquals(this.shelf.getShelf(), shelf);

        this.shelf.cleanShelf();
    }

    @Test
    public void cleanShelf_Test() {
        Tile tileC = new Tile(Type.CAT,1);
        Tile tileT = new Tile(Type.TROPHY,1);
        Tile tileP = new Tile(Type.PLANT,1);
        this.shelf.putTile(tileP, 0);
        this.shelf.putTile(tileP, 0);
        this.shelf.putTile(tileP, 0);
        this.shelf.putTile(tileC, 1);
        this.shelf.putTile(tileC, 1);
        this.shelf.putTile(tileC, 1);
        this.shelf.putTile(tileT, 2);
        this.shelf.putTile(tileT, 2);
        this.shelf.putTile(tileT, 2);

        this.shelf.cleanShelf();

        Optional<Tile>[][] temp = this.shelf.getShelf();

        for(int i=0; i<HEIGHT; i++){
            for(int j=0; j<WIDTH; j++){
                assertTrue(temp[i][j].isEmpty());
            }
        }

    }
}
