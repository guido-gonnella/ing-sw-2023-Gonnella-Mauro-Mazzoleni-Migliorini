package it.polimi.ingsw.Model;

import java.util.*;

import exceptions.SackEmptyException;
import org.junit.*;
import static org.junit.Assert.*;

public class GameTest {
    Game game = new Game();
    SackOfTiles sack = new SackOfTiles();
    DeckOfPublicObjectives deckOfPublicObjectives = new DeckOfPublicObjectives();

    @After
    public void reset() {
        game = new Game();
        sack = new SackOfTiles();
        deckOfPublicObjectives = new DeckOfPublicObjectives();
    }

    @Test
    public void addPlayer_and_getPlayerByNick_getNumPlayers() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        System.out.println("Test1: ");
        System.out.println(game.getPlayerByNick("Pietro").getNickname());
        System.out.println(game.getPlayerByNick("Tua Madre").getNickname());
        System.out.println(game.getPlayerByNick("lol").getNickname());
        System.out.println(game.getPlayerByNick("San").getNickname());
        System.out.println(game.getNumPlayers() + "\n");
    }

    @Test
    public void setBoard_4PlayersSetup() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < game.getBoard().ROW; i++) {
            for(int j = 0; j < game.getBoard().COL; j++) {
                if(game.getBoard().getGrid()[i][j].getTile().isPresent()) {
                    switch (game.getBoard().getGrid()[i][j].getTile().get().getType()) {
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

    @Test
    public void getPlayerList_AndSetFirstPlayer_4Players() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        for (String name : game.getPlayerList()) {
            System.out.println(name);
        }
        System.out.print("\n");

        game.setFirstPlayer("Tua Madre");
        System.out.println(game.getFirstPlayer() + "\n");
    }

    @Test
    public void setSackOfTiles_afterFillIn4PlayersBoard() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < game.getBoard().ROW; i++) {
            for(int j = 0; j < game.getBoard().COL; j++) {
                if(game.getBoard().getGrid()[i][j].getTile().isPresent()) {
                    switch (game.getBoard().getGrid()[i][j].getTile().get().getType()) {
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

        int tiles = game.getSackOfTiles().getLeftTiles().size();
        System.out.println(tiles + "\n");

        try{
            while(true) {
                Tile tile = sack.getRandomTile();
                game.setSackOfTiles(sack);
                System.out.println(game.getSackOfTiles().getLeftTiles().size() + "\n");
            }
        } catch (SackEmptyException e) {
            game.setSackOfTiles(sack);
            System.out.println(game.getSackOfTiles().getLeftTiles().size() + "\n");
        }
    }

    @Test
    public void takeTiles_from_4PlayersBoard() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        for(int i = 0; i < game.getBoard().ROW; i++) {
            for(int j = 0; j < game.getBoard().COL; j++) {
                if(game.getBoard().getGrid()[i][j].getTile().isPresent()) {
                    switch (game.getBoard().getGrid()[i][j].getTile().get().getType()) {
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

        game.takeTiles(5,5);
        game.takeTiles(4,5);

        for(int i = 0; i < game.getBoard().ROW; i++) {
            for(int j = 0; j < game.getBoard().COL; j++) {
                if(game.getBoard().getGrid()[i][j].getTile().isPresent()) {
                    switch (game.getBoard().getGrid()[i][j].getTile().get().getType()) {
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

    @Test
    public void selectTiles_validSelection_returnTrue_4PlayersBoard() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        game.selectTiles(0,3);
        game.selectTiles(0,4);

        assertTrue(game.validSelection());
    }

    @Test
    public void selectTiles_validSelection_returnFalse_4PlayersBoard() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        game.selectTiles(0,3);
        game.selectTiles(1,4);

        assertFalse(game.validSelection());
    }

    @Test
    public void selectTiles_2Tiles_tempTilesSelSize() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        game.selectTiles(0,3);
        game.selectTiles(0,4);

        assertEquals(game.tempTileSelSize(), 2);
    }

    @Test
    public void selectTiles_tilesInHandMethodsTest() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        Tile tile1 = game.getBoard().getGrid()[0][3].getTile().get();
        Tile tile2 = game.getBoard().getGrid()[0][4].getTile().get();

        game.selectTiles(0,3);
        game.selectTiles(0,4);

        game.fillTilesInHand();

        assertEquals(game.getTilesInCurrPlayerHand().get(0), tile1);
        assertEquals(game.getTilesInCurrPlayerHand().get(1), tile2);

        game.swapInHand(0, 1);

        assertEquals(game.getTilesInCurrPlayerHand().get(0), tile2);
        assertEquals(game.getTilesInCurrPlayerHand().get(1), tile1);
    }

    @Test
    public void publicObjectivesMethodsTest() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        game.setPublicObjectives(deckOfPublicObjectives);
        assertNotEquals(game.getPublicObjectives()[0], game.getPublicObjectives()[1]);
    }
}
