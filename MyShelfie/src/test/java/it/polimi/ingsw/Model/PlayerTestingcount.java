package it.polimi.ingsw.Model;


import exceptions.ColumnAlreadyFullException;
import exceptions.ImpossiblePrivateObjException;

import exceptions.OutOfShelfException;
import it.polimi.ingsw.Model.Enumeration.Type;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class PlayerTestingcount {
    Tile tile =null;
    Player tested;
    Shelf testshelf= new Shelf();
    PrivateObjective objective;
    ArrayList<ElementObjective> TestingList = new ArrayList<>();
    @Before
    public void setup() {
        tested=new Player("pietro");
        for (int j = 0; j < 5; j++) {
            TestingList.add(new ElementObjective( 5, j, Type.CAT));
        }

            try {
        objective = new PrivateObjective(TestingList);
    } catch (
    ImpossiblePrivateObjException e) {
                throw new RuntimeException(e);
            }
        tested.setPrivateObjective(objective);
        for (int j = 0; j < 5; j++) {
            try {
                tile=new Tile(Type.TROPHY);
                testshelf.putTile(tile, 0);
            } catch (ColumnAlreadyFullException e) {
                throw new RuntimeException(e);
            } catch (OutOfShelfException e) {
                throw new RuntimeException(e);
            }
            tested.placeTile(tile,0);
        }
        for (int j = 0; j < 5; j++) {
            try {
                tile=new Tile(Type.CAT);
                testshelf.putTile(tile, 1);
            } catch (ColumnAlreadyFullException e) {
                throw new RuntimeException(e);
            } catch (OutOfShelfException e) {
                throw new RuntimeException(e);
            }
            tested.placeTile(tile,1);
        }
        for (int j = 0; j < 5; j++) {
            try {
                tile=new Tile(Type.TROPHY);
                testshelf.putTile(tile, 2);
            } catch (ColumnAlreadyFullException e) {
                throw new RuntimeException(e);
            } catch (OutOfShelfException e) {
                throw new RuntimeException(e);
            }
            tested.placeTile(tile,2);
        }
        for (int j = 0; j < 5; j++) {
            try {
                tile=new Tile(Type.CAT);
                testshelf.putTile(tile, 3);
            } catch (ColumnAlreadyFullException e) {
                throw new RuntimeException(e);
            } catch (OutOfShelfException e) {
                throw new RuntimeException(e);
            }
            tested.placeTile(tile,3);
        }


    }
    @Test
    public void testing(){
        assertArrayEquals(tested.getShelf().getShelf(),testshelf.getShelf()); //[0][0].get().getType())
        tested.countPoints();

        int i =tested.getPlayerPoints();
        assertEquals(20,i);

    }
    @After
    public void down(){
        tested=null;
        testshelf=null;
        objective=null;
        TestingList=null;
    }

}
