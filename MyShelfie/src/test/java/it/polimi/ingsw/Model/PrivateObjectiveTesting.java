package it.polimi.ingsw.Model;
import exceptions.ImpossiblePrivateObjException;
import it.polimi.ingsw.Model.Enumeration.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
public class PrivateObjectiveTesting {
    PrivateObjective objective = null;
    ArrayList<ElementObjective> TestingList;
    @Before
    public void setup(){

        TestingList= new ArrayList<>();
    }

    @Test
    public void List_inserted_is_equal_to_output(){
            for (int j = 0; j < 5; j++) {
                TestingList.add(new ElementObjective(1, j, Type.TROPHY));
            }
        objective= new PrivateObjective(1);

        assertEquals(TestingList,objective.getObjective());
    }
    @Test
    public void every_element_inserted_is_equal_to_output(){
        for (int j = 0; j < 5; j++) {
            TestingList.add(new ElementObjective(1, j, Type.TROPHY));
        }
          objective =new PrivateObjective(1);

        for(int j=0;j<5;j++) assertEquals(TestingList.get(j).getY(),objective.getObjective().get(j).getY());
    }
    @After
    public void Teardown()
    {
        objective = null;
        TestingList = null;
    }

}

