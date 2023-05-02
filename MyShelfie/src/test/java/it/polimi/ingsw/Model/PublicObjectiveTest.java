package it.polimi.ingsw.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * This class tests the {@link PublicObjective} methods.
 */
public class PublicObjectiveTest {

    private Shelf shelf;

    private CommonObj commObj;

    @Before
    public void setUp(){
        shelf = new Shelf();
        Scanner scanner = new Scanner(System.in);
    }

    @After
    public void tearDown(){
        shelf = null;
        commObj = null;
    }

    @Test
    public void lambdaCross(){

    }



}
