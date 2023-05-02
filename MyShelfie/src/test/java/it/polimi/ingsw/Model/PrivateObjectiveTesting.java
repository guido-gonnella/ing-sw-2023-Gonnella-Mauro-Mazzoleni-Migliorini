package it.polimi.ingsw.Model;
import exceptions.ImpossiblePrivateObjException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
public class PrivateObjectiveTesting {
    PrivateObjective objective = null;
    ArrayList<ElementObjective> TestingList = new ArrayList<>();

    @Before
    public void setup(){
        for (int i=0; i<3;i++) {
            for (int j = 0; j < 3; j++) {
                TestingList.add(new ElementObjective(i, j, Type.TROPHY));
            }
        }
            try {
                objective= new PrivateObjective(TestingList);
            } catch (ImpossiblePrivateObjException e) {
                throw new RuntimeException(e);
            }

    }

    @Test
    public void correct_out(){
        assertEquals(TestingList,objective.getObjective());
    }
    @After
    public void Teardown()
    {
        objective = null;
        TestingList = null;
    }

}

