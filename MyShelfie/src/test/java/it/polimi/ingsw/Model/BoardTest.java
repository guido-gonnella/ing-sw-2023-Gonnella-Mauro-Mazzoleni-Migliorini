package it.polimi.ingsw.Model;

import exceptions.SackEmptyException;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void test1() {
        Board board = new Board();
        SackOfTiles sack = new SackOfTiles();

        try {
            board.fill(sack);
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }


    }
}
