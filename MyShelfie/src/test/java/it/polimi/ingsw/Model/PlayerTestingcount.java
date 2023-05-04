package it.polimi.ingsw.Model;


import exceptions.ColumnAlreadyFullException;
import exceptions.ImpossiblePrivateObjException;

import exceptions.OutOfShelfException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerTestingcount {
    Player tested;
    Shelf testshelf;
    PrivateObjective objective;
    ArrayList<ElementObjective> TestingList = null;
    @Before
    public void setup() {
        tested=new Player("pietro");
        for (int j = 0; j < 6; j++) {
            TestingList.add(new ElementObjective( j, 0, Type.CAT));
        }

            try {
        objective = new PrivateObjective(TestingList);
    } catch (
    ImpossiblePrivateObjException e) {
                throw new RuntimeException(e);
            }
        for (int j = 0; j < 6; j++) {
            try {
                testshelf.putTile(new Tile(Type.CAT), j);
            } catch (ColumnAlreadyFullException e) {
                throw new RuntimeException(e);
            } catch (OutOfShelfException e) {
                throw new RuntimeException(e);
            }
            tested.placeTile(new Tile(Type.CAT),j);
        }

    }
    @Test
    public void testing(){
        int j= tested.adjac
    }
}
