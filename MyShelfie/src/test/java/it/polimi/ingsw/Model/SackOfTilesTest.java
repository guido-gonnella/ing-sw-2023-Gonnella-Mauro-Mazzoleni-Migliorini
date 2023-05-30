package it.polimi.ingsw.Model;

import org.junit.*;
import static org.junit.Assert.*;

public class SackOfTilesTest {
    @Test
    public void getRandomTilesUntilThrown_SackEmptyException_countAllTilesTaken() {
        SackOfTiles sack = new SackOfTiles();
        int cat = 0;
        int book = 0;
        int game = 0;
        int trophy = 0;
        int plant = 0;
        int frame = 0;
        int tiles = 0;

        while(!sack.getLeftTiles().isEmpty()) {
            Tile tile = sack.getRandomTile();
            tiles++;
            switch (tile.getType()) {
                case CAT -> cat++;
                case BOOK -> book++;
                case GAME -> game++;
                case PLANT -> plant++;
                case FRAME -> frame++;
                case TROPHY -> trophy++;
                default -> System.out.println("Something's wrong...");
            }
        }

        System.out.println("Cat: " + cat);
        System.out.println("Book: " + book);
        System.out.println("Game: " + game);
        System.out.println("Plant: " + plant);
        System.out.println("Frame: " + frame);
        System.out.println("Trophy: " + trophy);
        System.out.println("Tiles: " + tiles);
    }
}
