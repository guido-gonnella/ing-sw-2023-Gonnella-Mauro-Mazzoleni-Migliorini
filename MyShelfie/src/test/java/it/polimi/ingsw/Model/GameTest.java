package it.polimi.ingsw.Model;

import java.util.*;

import exceptions.SackEmptyException;
import org.junit.*;
import static org.junit.Assert.*;

public class GameTest {
    Game game = new Game();
    SackOfTiles sack = new SackOfTiles();

    @Before
    public void reset() {
        game = new Game();
        sack = new SackOfTiles();
    }

    @Test
    public void test1() {
        game.addPlayer("Pietro");
        game.addPlayer("San");
        game.addPlayer("Tua Madre");
        game.addPlayer("lol");

        try {
            game.setBoard();
        } catch (SackEmptyException e) {
            System.out.println("Something's wrong...");
        }

        System.out.println("Test1: ");
        System.out.println(game.getPlayerByNick("Pietro").getNickname());
        System.out.println(game.getPlayerByNick("Tua Madre").getNickname());
        System.out.println(game.getPlayerByNick("lol").getNickname());
        System.out.println(game.getPlayerByNick("San").getNickname() + "\n");

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

        for (String name : game.getPlayerList()) {
            System.out.println(name);
        }
        System.out.print("\n");

        game.setFirstPlayer("Tua Madre");
        System.out.println(game.getFirstPlayer() + "\n");

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
}
