package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the {@link PublicObjective} methods.
 * @suthor Pierantonio Mauro
 */

public class SpaceTest {

    private Space space;

    @Before
    public void setUp(){
        this.space = new Space();
        assertFalse(space.isAvailable());
        assertTrue(space.getTile().isEmpty());
    }

    @After
    public void tearDown(){
        space = null;
    }

    @Test
    public void setAvailable_True(){
        space.setAvailable();
        assertTrue(space.isAvailable());
    }

    @Test
    public void setTile_Test(){
        Type type = Type.values()[1];
        Tile tile = new Tile(type);
        space.setTile(tile);
        System.out.println(space.getTile());
        assertTrue(space.getTile().isPresent());
        assertEquals(tile, space.getTile().get());
    }

    @Test
    public void removeTile_Test(){
        space.setTile(new Tile(Type.CAT));
        assertTrue(space.getTile().isPresent());
        space.removeTile();
        assertTrue(space.getTile().isEmpty());
    }
}
