package it.polimi.ingsw.Model;
import exceptions.ImpossiblePrivateObjException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import it.polimi.ingsw.Model.DeckOfPrivateObjectives;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
public class DeckOfPrivateObjectiveTesting {
    Type uno,due;
    DeckOfPrivateObjectives tested =null;
    PrivateObjective objective = null;
    ArrayList<ElementObjective> TestingList = new ArrayList<>();
    ArrayList<PrivateObjective> testobjectictives =new ArrayList<>();
    ArrayList<PrivateObjective> temp =null;
    @Before
    public void setup() {
        for (int x = 0; x < 6; x++) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    TestingList.add(new ElementObjective(i, j, Type.TROPHY));
                }
            }
            try {
                objective = new PrivateObjective(TestingList);
            } catch (ImpossiblePrivateObjException e) {
                throw new RuntimeException(e);
            }
            TestingList.subList(0, 4).clear();
            testobjectictives.add(objective);
        }
        for (int x = 0; x < 6; x++) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    TestingList.add(new ElementObjective(i, j, Type.CAT));
                }
            }
            try {
                objective = new PrivateObjective(TestingList);
            } catch (ImpossiblePrivateObjException e) {
                throw new RuntimeException(e);
            }
            TestingList.subList(0, 4).clear();
            testobjectictives.add(objective);
        }
        tested = new DeckOfPrivateObjectives(testobjectictives);
    }
    @Test
    public void correct_out(){
        temp=tested.getPrivateObjectives(2);
        uno=temp.get(1).getObjective().get(1).getType();
        due=testobjectictives.get(1).getObjective().get(1).getType();
        assertEquals(uno,due);
    }
    @After
    public void Teardown()
    {
        objective = null;
        TestingList = null;
        testobjectictives= null;
        tested = null;
        temp = null;
    }

}
