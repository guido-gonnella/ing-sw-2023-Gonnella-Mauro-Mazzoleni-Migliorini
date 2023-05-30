package it.polimi.ingsw.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTestEmoji {
    Board board = new Board();
    SackOfTiles sack = new SackOfTiles();

    @Before
    public void reset() {
        board = new Board();
        sack = new SackOfTiles();
    }

    @Test
    public void boardTestWithEmoji() {
        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        assertFalse(board.checkFill());

        System.out.println("Test1:");

        System.out.print("- 0  1  2  3  4  5  6  7  8\n");
        for(int i = 0; i < board.ROW; i++) {
            System.out.print(i);
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[\uD83C\uDFC6]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[\uD83C\uDF04]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[\uD83C\uDF3B]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[\uD83C\uDFB2]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[\uD83D\uDCDA]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[\uD83D\uDC08]" + "\u001B[0m");
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

        assertFalse(board.checkFill());

        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                if(board.getGrid()[i][j].getTile().isPresent()) {
                    switch (board.getGrid()[i][j].getTile().get().getType()) {
                        case TROPHY -> System.out.print("\u001B[36m" + "[\uD83C\uDFC6]" + "\u001B[0m");
                        case FRAME -> System.out.print("\u001B[34m" + "[\uD83C\uDF04]" + "\u001B[0m");
                        case PLANT -> System.out.print("\u001B[35m" + "[\uD83C\uDF3B]" + "\u001B[0m");
                        case GAME -> System.out.print("\u001B[33m" + "[\uD83C\uDFB2]" + "\u001B[0m");
                        case BOOK -> System.out.print("\u001B[37m" + "[\uD83D\uDCDA]" + "\u001B[0m");
                        case CAT -> System.out.print("\u001B[32m" + "[\uD83D\uDC08]" + "\u001B[0m");
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
