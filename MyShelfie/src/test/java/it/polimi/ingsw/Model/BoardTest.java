package it.polimi.ingsw.Model;

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
    public void fill_allBoardSetAvailable_checkFill_PreFillAndPostFill() {
        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        assertFalse(board.checkFill());

        System.out.println("Test1:");

        System.out.print("-  0  1  2  3  4  5  6  7  8\n");
        for(int i = 0; i < board.ROW; i++) {
            System.out.print(i + " ");
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
    public void fill_5x5BoardSetAvailable_checkFill_PreFillAndPostFill() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        assertFalse(board.checkFill());

        System.out.println("Test2:");

        print_board(board);

        assertTrue(board.checkFill());
    }

    @Test
    public void fill_TwoSpacesSetAvailable_fillWithThirdSpaceSetAvailable_checkFillOnBoth() {
        System.out.println("Test3:");

        board.getGrid()[0][0].setAvailable();
        board.getGrid()[1][1].setAvailable();

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
    public void fill_AllBoardSetAvailable_takeTilesOnCenterTile_checkFillOnBoth() {
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

    @Test
    public void setBoard_4Players_emptyBoard_fill_multipleTimes() {
        for(int i = 0; i < board.ROW; i++) {
            for(int j = 0; j < board.COL; j++) {
                board.getGrid()[i][j].setAvailable();
            }
        }

        board.fill(sack);

        print_board(board);

        for(int i=0; i< board.ROW && board.checkFill(); i++) {
            for(int j=0; j< board.COL && board.checkFill(); j++) {
                board.takeTiles(i, j);
            }
        }

        board.fill(sack);

        print_board(board);
    }

    public void print_board(Board board){
        System.out.print("-");
        for (int i=0;i<board.COL;i++) {
            System.out.print("\u001B[30m" + "-" + "\u001B[0m" + i + "\u001B[30m" + "-" + "\u001B[0m");
        }
        System.out.print("\n");
        for(int i = 0; i < board.ROW; i++) {
            System.out.print(i);
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
    }
}
