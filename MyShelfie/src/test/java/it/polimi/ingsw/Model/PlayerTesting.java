package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Enumeration.Type;
import org.junit.Assert;
import org.junit.Test;
import org.junit.After;

import static org.junit.Assert.*;

public class PlayerTesting {
    Player player = new Player("Asdrubale");

    @After
    public void reset() {
        player = new Player("Asdrubale");
    }

    @Test
    public void placeTile_test() {
        Tile t1 = new Tile(Type.CAT,1);
        Tile t2 = new Tile(Type.BOOK,1);
        Tile t3 = new Tile(Type.PLANT,1);
        Tile t4 = new Tile(Type.GAME,1);
        Tile t5 = new Tile(Type.FRAME,1);
        Tile t6 = new Tile(Type.TROPHY,1);

        player.placeTile(t1, 0);
        player.placeTile(t2, 0);
        player.placeTile(t3, 0);
        player.placeTile(t4, 0);
        player.placeTile(t5, 0);
        player.placeTile(t6, 0);

        System.out.print("-  0  1  2  3  4\n");
        for(int i = 0; i < player.getShelf().getHEIGHT(); i++) {
            System.out.print(i + " ");
            for(int j = 0; j < player.getShelf().getWIDTH(); j++) {
                if(player.getShelf().getShelf()[i][j].isPresent()) {
                    switch (player.getShelf().getShelf()[i][j].get().getType()) {
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

        player.placeTile(t6, 0);
        player.placeTile(t6, 5);
    }

    @Test
    public void addPoints_playerPointsAttribute_test() {
        System.out.println(player.getPlayerPoints());

        player.addPoints(5);
        System.out.println(player.getPlayerPoints());

        player.addPoints(2);
        System.out.println(player.getPlayerPoints());
    }

    @Test
    public void countPoints_pointsMethods_and_setPrivateObjective_test() {
        PrivateObjective privateObjective = new PrivateObjective(2);

        player.setPrivateObjective(privateObjective);

        Tile t1 = new Tile(Type.CAT,1);
        Tile t2 = new Tile(Type.BOOK,1);
        Tile t3 = new Tile(Type.PLANT,1);
        Tile t4 = new Tile(Type.GAME,1);
        Tile t5 = new Tile(Type.FRAME,1);
        Tile t6 = new Tile(Type.TROPHY,1);

        player.placeTile(t1, 0);
        player.placeTile(t2, 0);
        player.placeTile(t3, 0);
        player.placeTile(t1, 0);
        player.placeTile(t1, 0);
        player.placeTile(t1, 0);
        player.placeTile(t4, 1);
        player.placeTile(t4, 1);
        player.placeTile(t4, 1);
        player.placeTile(t1, 1);
        player.placeTile(t1, 1);
        player.placeTile(t1, 1);
        player.placeTile(t5, 2);
        player.placeTile(t2, 2);
        player.placeTile(t3, 2);
        player.placeTile(t3, 2);
        player.placeTile(t3, 2);
        player.placeTile(t2, 2);
        player.placeTile(t6, 3);
        player.placeTile(t6, 3);
        player.placeTile(t3, 3);
        player.placeTile(t1, 3);
        player.placeTile(t5, 4);
        player.placeTile(t6, 4);
        player.placeTile(t6, 4);
        player.placeTile(t6, 4);

        System.out.print("-  0  1  2  3  4\n");
        for(int i = 0; i < player.getShelf().getHEIGHT(); i++) {
            System.out.print(i + " ");
            for(int j = 0; j < player.getShelf().getWIDTH(); j++) {
                if(player.getShelf().getShelf()[i][j].isPresent()) {
                    switch (player.getShelf().getShelf()[i][j].get().getType()) {
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

        player.countPoints();

        assertEquals(22, player.getPlayerPoints());
    }

    @Test
    public void updatePubObjFlag_and_getPubObjFlag_test() {
        assertFalse(player.getPubObjFlag()[0]);
        assertFalse(player.getPubObjFlag()[1]);
        player.updatePubObjFlag(0);


        assertTrue(player.getPubObjFlag()[0]);
        player.updatePubObjFlag(0);
    }

    @Test
    public void getNickname_test() {
        assertEquals(player.getNickname(), "Asdrubale");
    }

    @Test
    public void getPrivateObjective_test() {
        PrivateObjective privateObjective = new PrivateObjective(2);

        player.setPrivateObjective(privateObjective);

        assertEquals(player.getPrivateObjective(), privateObjective);
    }

    @Test
    public void getFreeSpaceInCol_withShelfNotEmpty() {
        Tile t1 = new Tile(Type.CAT,1);
        Tile t2 = new Tile(Type.BOOK,1);
        Tile t3 = new Tile(Type.PLANT,1);
        Tile t4 = new Tile(Type.GAME,1);
        Tile t5 = new Tile(Type.FRAME,1);
        Tile t6 = new Tile(Type.TROPHY,1);

        player.placeTile(t1, 0);
        player.placeTile(t2, 0);
        player.placeTile(t3, 0);
        player.placeTile(t1, 0);
        player.placeTile(t1, 0);
        player.placeTile(t1, 0);
        player.placeTile(t4, 1);
        player.placeTile(t4, 1);
        player.placeTile(t4, 1);
        player.placeTile(t1, 1);
        player.placeTile(t1, 1);
        player.placeTile(t5, 2);
        player.placeTile(t2, 2);
        player.placeTile(t3, 2);
        player.placeTile(t3, 2);
        player.placeTile(t6, 3);
        player.placeTile(t6, 3);
        player.placeTile(t3, 3);
        player.placeTile(t5, 4);
        player.placeTile(t6, 4);

        System.out.print("-  0  1  2  3  4\n");
        for(int i = 0; i < player.getShelf().getHEIGHT(); i++) {
            System.out.print(i + " ");
            for(int j = 0; j < player.getShelf().getWIDTH(); j++) {
                if(player.getShelf().getShelf()[i][j].isPresent()) {
                    switch (player.getShelf().getShelf()[i][j].get().getType()) {
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

        assertEquals(player.getFreeSpaceInCol(0), 0);
        assertEquals(player.getFreeSpaceInCol(1), 1);
        assertEquals(player.getFreeSpaceInCol(2), 2);
        assertEquals(player.getFreeSpaceInCol(3), 3);
        assertEquals(player.getFreeSpaceInCol(4), 4);
    }
}
