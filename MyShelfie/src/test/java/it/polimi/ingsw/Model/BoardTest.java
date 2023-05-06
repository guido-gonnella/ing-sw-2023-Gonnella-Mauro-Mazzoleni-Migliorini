package it.polimi.ingsw.Model;

import exceptions.SackEmptyException;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class BoardTest {
    Board board = new Board();
    SackOfTiles sack = new SackOfTiles();

    @Before
    public void reset() {
        board = new Board();
        sack = new SackOfTiles();
    }

    @Test
    public void test1() {
        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        assertFalse(board.checkFill());

        System.out.println("Test1:");

        try {
            board.fill(sack);
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        assertTrue(board.checkFill());
    }

    @Test
    public void test2() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        assertFalse(board.checkFill());

        System.out.println("Test2:");

        try {
            board.fill(sack);
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        assertTrue(board.checkFill());
    }

    @Test
    public void test3() {
        System.out.println("Test3:");

        board.getGrid()[0][0].setAvailable();
        board.getGrid()[1][1].setAvailable();

        try {
            board.fill(sack);
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        assertFalse(board.checkFill());

        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        board.getGrid()[0][1].setAvailable();

        try {
            board.fill(sack);
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        assertTrue(board.checkFill());
    }

    @Test
    public void test4() {
        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        assertFalse(board.checkFill());

        System.out.println("Test4:");

        try {
            board.fill(sack);
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        assertTrue(board.checkFill());

        board.takeTiles(4,4);

        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        assertTrue(board.checkFill());
    }
}
