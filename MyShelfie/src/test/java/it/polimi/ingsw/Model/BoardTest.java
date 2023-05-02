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

        final int ROW = 9;
        final int COL = 9;

        for(int i = 0; i < ROW; i++) {
            for(int j = 0; j < COL; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        try {
            board.fill(sack);
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < ROW; i++) {
            for(int j = 0; j < COL; j++) {
                System.out.println("[" + board.getGrid()[i][j].getTile().get().getType() + "]");
            }
            System.out.println("\n");
        }
    }
}
