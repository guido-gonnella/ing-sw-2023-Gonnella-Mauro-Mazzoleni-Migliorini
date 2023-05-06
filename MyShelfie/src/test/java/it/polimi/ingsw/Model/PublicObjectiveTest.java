package it.polimi.ingsw.Model;

import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import it.polimi.ingsw.Model.Enumeration.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * This class tests the {@link PublicObjective} methods.
 */
public class PublicObjectiveTest {

    private Shelf shelf;

    private CommonObj commObj;

    private final int WIDTH = 5;
    private final int HEIGHT = 6;

    @Before
    public void setUp(){
        this.shelf = new Shelf();
    }

    @After
    public void tearDown(){
        this.shelf = null;
        this.commObj = null;
    }

    /**
     * Testing of the labda function that see if there's a "cross"
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_cross() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true, elementary
        Shelf shelf2 = new Shelf(); // true, not elementary
        Shelf shelf3 = new Shelf(); // false, not elementary
        Shelf shelf4 = new Shelf(); // false, no tiles
        Tile tileC = new Tile(Type.CAT);
        Tile tileP = new Tile(Type.PLANT);
        Tile tileT = new Tile(Type.TROPHY);
        PublicObjective pubObj_cross = new PublicObjective("cross");

        //shelf1
        shelf1.putTile(tileC, 0);
        shelf1.putTile(tileP, 0);
        shelf1.putTile(tileC, 0);
        shelf1.putTile(tileP, 1);
        shelf1.putTile(tileC, 1);
        shelf1.putTile(tileP, 1);
        shelf1.putTile(tileC, 2);
        shelf1.putTile(tileP, 2);
        shelf1.putTile(tileC, 2);
        assertTrue(pubObj_cross.getResultObjective(shelf1));

        //shelf2
        for(int i=0; i<HEIGHT; i++){
            shelf2.putTile(tileC, 0);
            shelf2.putTile(tileC, WIDTH-1);
        }
        shelf2.putTile(tileP, 1);
        shelf2.putTile(tileP, 2);
        shelf2.putTile(tileP, 3);
        shelf2.putTile(tileT, 1);
        shelf2.putTile(tileP, 2);
        shelf2.putTile(tileT, 3);
        shelf2.putTile(tileP, 1);
        shelf2.putTile(tileT, 2);
        shelf2.putTile(tileP, 3);
        shelf2.putTile(tileT, 1);
        shelf2.putTile(tileP, 2);
        shelf2.putTile(tileT, 3);
        for(int i=0; i<2; i++){
            shelf2.putTile(tileP, 1);
            shelf2.putTile(tileP, 2);
            shelf2.putTile(tileP, 3);
        }
        assertTrue(pubObj_cross.getResultObjective(shelf2));

        //shelf3
        for(int i=0; i<HEIGHT; i++){
            shelf3.putTile(tileC, 0);
            shelf3.putTile(tileC, WIDTH-1);
        }
        shelf3.putTile(tileP, 1);
        shelf3.putTile(tileP, 2);
        shelf3.putTile(tileP, 3);
        shelf3.putTile(tileT, 1);
        shelf3.putTile(tileT, 2);
        shelf3.putTile(tileT, 3);
        shelf3.putTile(tileP, 1);
        shelf3.putTile(tileP, 2);
        shelf3.putTile(tileP, 3);
        shelf3.putTile(tileT, 1);
        shelf3.putTile(tileT, 2);
        shelf3.putTile(tileT, 3);
        assertFalse(pubObj_cross.getResultObjective(shelf3));

        //shelf4
        assertFalse(pubObj_cross.getResultObjective(shelf4));

    }

    /**
     * Lambda function that see if there is a "stair" on the shelf
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_stair() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true, LR
        Shelf shelf2 = new Shelf(); // true, RL
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT);
        Tile tileB = new Tile(Type.PLANT);
        Tile tileC = new Tile(Type.BOOK);
        PublicObjective pubObj = new PublicObjective("stair");

        //shelf1
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileA, 1);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileA, 2);
        shelf1.putTile(tileA, 2);
        shelf1.putTile(tileC, 2);
        shelf1.putTile(tileC, 3);
        shelf1.putTile(tileA, 3);
        shelf1.putTile(tileC, 3);
        shelf1.putTile(tileB, 3);
        shelf1.putTile(tileA, 4);
        shelf1.putTile(tileC, 4);
        shelf1.putTile(tileB, 4);
        shelf1.putTile(tileB, 4);
        shelf1.putTile(tileA, 4);
        assertTrue(pubObj.getResultObjective(shelf1));

        //shelf2
        shelf2.putTile(tileA, 4);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileB, 3);
        shelf2.putTile(tileA, 2);
        shelf2.putTile(tileA, 2);
        shelf2.putTile(tileC, 2);
        shelf2.putTile(tileC, 1);
        shelf2.putTile(tileA, 1);
        shelf2.putTile(tileC, 1);
        shelf2.putTile(tileB, 1);
        shelf2.putTile(tileA, 0);
        shelf2.putTile(tileC, 0);
        shelf2.putTile(tileB, 0);
        shelf2.putTile(tileB, 0);
        shelf2.putTile(tileA, 0);
        assertTrue(pubObj.getResultObjective(shelf2));

        //shelf3
        shelf3.putTile(tileA, 0);
        shelf3.putTile(tileA, 1);
        shelf3.putTile(tileB, 1);
        shelf3.putTile(tileA, 2);
        shelf3.putTile(tileA, 2);
        shelf3.putTile(tileC, 2); // dopo di questa c'è un'altra linea in shelf1
        shelf3.putTile(tileA, 3);
        shelf3.putTile(tileC, 3);
        shelf3.putTile(tileB, 3);
        shelf3.putTile(tileA, 4);
        shelf3.putTile(tileC, 4);
        shelf3.putTile(tileB, 4);
        shelf3.putTile(tileB, 4);
        shelf3.putTile(tileA, 4);
        assertFalse(pubObj.getResultObjective(shelf3));

        //shelf4
        shelf4.putTile(tileB, 3);
        shelf4.putTile(tileA, 2);
        shelf4.putTile(tileA, 2);
        shelf4.putTile(tileC, 1);
        shelf4.putTile(tileC, 1);
        shelf4.putTile(tileA, 1);
        shelf4.putTile(tileC, 0);
        shelf4.putTile(tileB, 0);
        shelf4.putTile(tileB, 0);
        shelf4.putTile(tileA, 0);
        assertFalse(pubObj.getResultObjective(shelf4));
    }

    //lambda da rivedere
    @Test
    public void lambda_six_couples() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT);
        Tile tileB = new Tile(Type.PLANT);
        Tile tileC = new Tile(Type.TROPHY);
        Tile tileD = new Tile(Type.BOOK);
        Tile tileE = new Tile(Type.FRAME);
        Tile tileF = new Tile(Type.GAME);
        CommonObj SixTwoEqual = (shelf) -> {
            Optional<Tile>[][] temp = shelf.getShelf();
            boolean[][] check = new boolean[6][5];
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    check[i][j] = false;
                }
            }
            int count = 0;  //counter for the groups of four adjacent equal tiles

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!check[i][j] && temp[i][j].isPresent()) {
                        Queue<Coords> q = new ArrayDeque<Coords>();
                        int nEqualTiles = 1;

                        q.add(new Coords(i, j));
                        check[i][j] = true;

                        while (!q.isEmpty() && nEqualTiles < 2) {
                            int x = q.peek().x, y = q.peek().y;
                            q.poll();

                            if ((temp[x + 1][y].isEmpty() || temp[x + 1][y].get().getType().equals(temp[x][y].get().getType())) && !check[x + 1][y] && x + 1 >= 0 && x + 1 < 6) {
                                q.add(new Coords(x + 1, y));
                                nEqualTiles++;
                                check[x + 1][j] = true;
                            }
                            if ((temp[x - 1][y].isEmpty() || temp[x - 1][y].get().getType().equals(temp[x][y].get().getType())) && !check[x - 1][y] && x - 1 >= 0 && x - 1 < 6) {
                                q.add(new Coords(x - 1, y));
                                nEqualTiles++;
                                check[x - 1][y] = true;
                            }
                            if ((temp[x][y + 1].isEmpty() || temp[x][y + 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y + 1] && y + 1 >= 0 && y + 1 < 5) {
                                q.add(new Coords(x, y + 1));
                                nEqualTiles++;
                                check[x][y + 1] = true;
                            }
                            if ((temp[x][y - 1].isEmpty() || temp[x][y - 1].get().getType().equals(temp[x][y].get().getType())) && !check[x][y - 1] && y - 1 >= 0 && y - 1 < 5) {
                                q.add(new Coords(x, y - 1));
                                nEqualTiles++;
                                check[x][y - 1] = true;
                            }
                        }
                        if (nEqualTiles == 2) count++;
                    }
                }
            }
            if (count >= 6) return true;
            return false;
        };
        PublicObjective pubObj_sixCouples = new PublicObjective("SixTwoEqual");

        //shelf1
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileB, 2);
        shelf1.putTile(tileA, 3);
        shelf1.putTile(tileA, 3);
        shelf1.putTile(tileB, 4);
        shelf1.putTile(tileB, 4);
        shelf1.putTile(tileC, 1);
        shelf1.putTile(tileC, 1);
        shelf1.putTile(tileD, 3);
        shelf1.putTile(tileD, 4);
        assertTrue(pubObj_sixCouples.getResultObjective(shelf1));

    }

    //lambda da rivedere
    @Test
    public void lambda_four_quadruple() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT);
        Tile tileB = new Tile(Type.PLANT);
        Tile tileC = new Tile(Type.TROPHY);
        Tile tileD = new Tile(Type.BOOK);
        Tile tileE = new Tile(Type.FRAME);
        Tile tileF = new Tile(Type.GAME);
        CommonObj checkClustersFour = (shelf) -> {
            Optional<Tile>[][] temp = shelf.getShelf();
            int count = 0;
            boolean[][] visited = new boolean[6][5];

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (!visited[i][j] && temp[i][j].isPresent()) {
                        Tile value = temp[i][j].get();
                        visited[i][j] = true;

                        boolean top = false;
                        boolean bottom = false;
                        boolean left = false;
                        boolean right = false;
                        if(temp[i-1][j].isPresent())
                            top = (i > 0 && temp[i-1][j].get().equals(value) && !visited[i-1][j]);
                        if(temp[i+1][j].isPresent())
                            bottom = (i < 6-1 && temp[i+1][j].get().equals(value) && !visited[i+1][j]);
                        if(temp[i][j-1].isPresent())
                            left = (j > 0 && temp[i][j-1].get().equals(value) && !visited[i][j-1]);
                        if(temp[i][j+1].isPresent())
                            right = (j < 5-1 && temp[i][j+1].get().equals(value) && !visited[i][j+1]);

                        if (top || bottom || left || right) {
                            count++;
                            checkAdjacent(temp, visited, i, j, value);
                        }
                    }
                }
            }

            return count >= 4;
        };
        PublicObjective pubObj = new PublicObjective("checkClustersFour");

        //shelf1
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileA, 1);
        shelf1.putTile(tileA, 1);
        shelf1.putTile(tileB, 2);
        shelf1.putTile(tileB, 2);
        shelf1.putTile(tileB, 2);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileC, 0);
        shelf1.putTile(tileC, 0);
        shelf1.putTile(tileC, 0);
        shelf1.putTile(tileC, 1);
        shelf1.putTile(tileD, 3);
        shelf1.putTile(tileD, 3);
        shelf1.putTile(tileD, 3);
        shelf1.putTile(tileD, 3);
        assertTrue(pubObj.getResultObjective(shelf1));
    }

    /**
     * Lambda function that assures that all four angles have the same type of tile
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_four_angles() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true
        Shelf shelf2 = new Shelf(); // false, wrong
        Shelf shelf3 = new Shelf(); // false, missing
        Tile tileC = new Tile(Type.CAT);
        Tile tileP = new Tile(Type.PLANT);
        PublicObjective pubObj_angles = new PublicObjective("angles");

        for(int i=0; i<HEIGHT; i++){
            shelf1.putTile(tileC, 0);
            shelf1.putTile(tileC, WIDTH-1);
        }

        for(int i=0; i<HEIGHT; i++){
            shelf2.putTile(tileC, 0);
            if(i<3){
                shelf2.putTile(tileC, WIDTH-1);
            }
            else{
                shelf2.putTile(tileP, WIDTH-1);
            }
        }

        for(int i=0; i<HEIGHT; i++){
            shelf3.putTile(tileC, 0);
            if(i<3){
                shelf3.putTile(tileC, WIDTH-1);
            }
        }

        assertTrue(pubObj_angles.getResultObjective(shelf1));
        assertFalse(pubObj_angles.getResultObjective(shelf2));
        assertFalse(pubObj_angles.getResultObjective(shelf3));
    }

    /**
     * Lambda function that see if there are two squares of the same typr of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_two_squares() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // false, easy
        Shelf shelf2 = new Shelf(); // true, easy
        Shelf shelf3 = new Shelf(); // true, hard
        Shelf shelf4 = new Shelf(); // false
        Tile tileC = new Tile(Type.CAT);
        Tile tileP = new Tile(Type.PLANT);
        Tile tileT = new Tile(Type.TROPHY);
        PublicObjective pubObj_squares = new PublicObjective("twoSquares");

        //shelf1
        shelf1.putTile(tileC,0);
        shelf1.putTile(tileC,0);
        shelf1.putTile(tileC,1);
        shelf1.putTile(tileC,1);
        shelf1.putTile(tileP,0);
        shelf1.putTile(tileP,0);
        shelf1.putTile(tileP,1);
        shelf1.putTile(tileP,1);
        assertFalse(pubObj_squares.getResultObjective(shelf1));

        //shelf2
        shelf2.putTile(tileC,0);
        shelf2.putTile(tileC,0);
        shelf2.putTile(tileC,1);
        shelf2.putTile(tileC,1);
        shelf2.putTile(tileC,0);
        shelf2.putTile(tileC,0);
        shelf2.putTile(tileC,1);
        shelf2.putTile(tileC,1);
        assertTrue(pubObj_squares.getResultObjective(shelf2));

        //shelf3
        shelf3.putTile(tileC,0);
        shelf3.putTile(tileC,0);
        shelf3.putTile(tileC,1);
        shelf3.putTile(tileC,1);
        shelf3.putTile(tileP, 0);
        shelf3.putTile(tileP, 1);
        shelf3.putTile(tileP,2);
        shelf3.putTile(tileP,2);
        shelf3.putTile(tileP,2);
        shelf3.putTile(tileC,3);
        shelf3.putTile(tileC,3);
        shelf3.putTile(tileC,4);
        shelf3.putTile(tileC,4);
        assertTrue(pubObj_squares.getResultObjective(shelf3));

        //shelf4
        shelf4.putTile(tileC,0);
        shelf4.putTile(tileC,0);
        shelf4.putTile(tileC,1);
        shelf4.putTile(tileC,1);
        shelf4.putTile(tileP, 0);
        shelf4.putTile(tileP, 1);
        shelf4.putTile(tileP,2);
        shelf4.putTile(tileP,2);
        shelf4.putTile(tileP,2);
        shelf4.putTile(tileT,3);
        shelf4.putTile(tileT,3);
        shelf4.putTile(tileT,4);
        shelf4.putTile(tileT,4);
        assertFalse(pubObj_squares.getResultObjective(shelf4));
    }

    /**
     * Lambda function that see if there are three full column with max 3 different types of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_three_similar_columns() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT);
        Tile tileB = new Tile(Type.PLANT);
        Tile tileC = new Tile(Type.TROPHY);
        Tile tileD = new Tile(Type.BOOK);
        Tile tileE = new Tile(Type.FRAME);
        Tile tileF = new Tile(Type.GAME);
        PublicObjective pubObj_threeCol = new PublicObjective("colThreeTypes");

        //shelf1
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileB, 0);
        shelf1.putTile(tileB, 0);
        shelf1.putTile(tileC, 0);
        shelf1.putTile(tileC, 0);
        shelf1.putTile(tileA, 1);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileA, 1);
        shelf1.putTile(tileA, 1);
        shelf1.putTile(tileA, 2);
        shelf1.putTile(tileC, 2);
        shelf1.putTile(tileB, 2);
        shelf1.putTile(tileB, 2);
        shelf1.putTile(tileA, 2);
        shelf1.putTile(tileC, 2);
        assertTrue(pubObj_threeCol.getResultObjective(shelf1));

        //shelf2
        shelf2.putTile(tileA, 0);
        shelf2.putTile(tileA, 0);
        shelf2.putTile(tileB, 0);
        shelf2.putTile(tileB, 0);
        shelf2.putTile(tileC, 0);
        shelf2.putTile(tileA, 1);
        shelf2.putTile(tileB, 1);
        shelf2.putTile(tileB, 1);
        shelf2.putTile(tileA, 1);
        shelf2.putTile(tileA, 1);
        shelf2.putTile(tileF, 1);
        shelf2.putTile(tileA, 2);
        shelf2.putTile(tileB, 2);
        shelf2.putTile(tileC, 2);
        shelf2.putTile(tileD, 2);
        shelf2.putTile(tileE, 2);
        shelf2.putTile(tileF, 2);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileC, 4);
        shelf2.putTile(tileC, 4);
        shelf2.putTile(tileC, 4);
        shelf2.putTile(tileD, 4);
        shelf2.putTile(tileD, 4);
        shelf2.putTile(tileD, 4);
        assertTrue(pubObj_threeCol.getResultObjective(shelf2));

        //shelf3
        shelf3.putTile(tileA, 0);
        shelf3.putTile(tileA, 0);
        shelf3.putTile(tileA, 0);
        shelf3.putTile(tileA, 0);
        shelf3.putTile(tileA, 0);
        shelf3.putTile(tileA, 0);
        assertFalse(pubObj_threeCol.getResultObjective(shelf3));

        //shelf4
        shelf4.putTile(tileA, 0);
        shelf4.putTile(tileB, 0);
        shelf4.putTile(tileC, 0);
        shelf4.putTile(tileA, 0);
        shelf4.putTile(tileB, 0);
        shelf4.putTile(tileF, 1);
        shelf4.putTile(tileF, 1);
        shelf4.putTile(tileF, 1);
        shelf4.putTile(tileF, 1);
        shelf4.putTile(tileF, 1);
        shelf4.putTile(tileF, 1);
        shelf4.putTile(tileD, 2);
        shelf4.putTile(tileE, 2);
        shelf4.putTile(tileF, 2);
        shelf4.putTile(tileD, 2);
        shelf4.putTile(tileE, 2);
        shelf4.putTile(tileF, 2);
        shelf4.putTile(tileA, 3);
        shelf4.putTile(tileB, 3);
        shelf4.putTile(tileC, 3);
        shelf4.putTile(tileA, 3);
        shelf4.putTile(tileB, 3);
        shelf4.putTile(tileD, 3);
        assertFalse(pubObj_threeCol.getResultObjective(shelf4));

    }

    /**
     * Lambda function that see if there are four full rows with max 3 different types of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_four_similar_rows() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true
        Shelf shelf2 = new Shelf(); // false
        Tile tileA = new Tile(Type.CAT);
        Tile tileB = new Tile(Type.PLANT);
        Tile tileC = new Tile(Type.TROPHY);
        Tile tileD = new Tile(Type.BOOK);
        Tile tileE = new Tile(Type.FRAME);
        Tile tileF = new Tile(Type.GAME);
        PublicObjective pubObj_fourRows = new PublicObjective("rowThreeTypes");

        //shelf1
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileA, 1);
        shelf1.putTile(tileA, 2);
        shelf1.putTile(tileA, 3);
        shelf1.putTile(tileA, 4);
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileC, 2);
        shelf1.putTile(tileA, 3);
        shelf1.putTile(tileB, 4);
        shelf1.putTile(tileA, 0);
        shelf1.putTile(tileB, 1);
        shelf1.putTile(tileA, 2);
        shelf1.putTile(tileB, 3);
        shelf1.putTile(tileA, 4);
        shelf1.putTile(tileD, 0);
        shelf1.putTile(tileE, 1);
        shelf1.putTile(tileF, 2);
        shelf1.putTile(tileD, 3);
        shelf1.putTile(tileE, 4);
        shelf1.putTile(tileD, 0);
        shelf1.putTile(tileE, 1);
        shelf1.putTile(tileF, 2);
        assertTrue(pubObj_fourRows.getResultObjective(shelf1));

        //shelf2
        shelf2.putTile(tileA, 0);
        shelf2.putTile(tileA, 1);
        shelf2.putTile(tileA, 2);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileA, 4);
        shelf2.putTile(tileA, 0);
        shelf2.putTile(tileB, 1);
        shelf2.putTile(tileC, 2);
        shelf2.putTile(tileA, 3);
        shelf2.putTile(tileB, 4);
        shelf2.putTile(tileD, 0);
        shelf2.putTile(tileE, 1);
        shelf2.putTile(tileF, 2);
        shelf2.putTile(tileD, 3);
        shelf2.putTile(tileE, 4);
        shelf2.putTile(tileA, 0);
        shelf2.putTile(tileB, 1);
        shelf2.putTile(tileC, 2);
        shelf2.putTile(tileD, 3);
        shelf2.putTile(tileE, 4);
        shelf2.putTile(tileA, 0);
        shelf2.putTile(tileB, 1);
        shelf2.putTile(tileC, 2);
        shelf2.putTile(tileD, 3);
        assertFalse(pubObj_fourRows.getResultObjective(shelf2));
    }

    /**
     * Lambda function that see if there are eight tiles of the same type
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_eight_same() throws ColumnAlreadyFullException, OutOfShelfException {
        int k;
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false
        Tile tileC = new Tile(Type.CAT);
        Tile tileP = new Tile(Type.PLANT);
        Tile tileT = new Tile(Type.TROPHY);
        Tile tileB = new Tile(Type.BOOK);
        Tile tileF = new Tile(Type.FRAME);
        PublicObjective pubObj_eight = new PublicObjective("eight");

        //shelf1
        for(k=0; k<HEIGHT; k++) {
            shelf1.putTile(tileC, 0);
            shelf1.putTile(tileC, 1);
        }
        assertTrue(pubObj_eight.getResultObjective(shelf1));

        //shelf2
        for(k=0; k<HEIGHT; k++){
            if(k<3)
                shelf2.putTile(tileC, 0);
            else
                shelf2.putTile(tileB, 0);
            shelf2.putTile(tileP, 1);
            shelf2.putTile(tileT, 2);
            shelf2.putTile(tileC,3);
        }
        assertTrue(pubObj_eight.getResultObjective(shelf2));

        //shelf3
        for(k=0; k<HEIGHT; k++){
            shelf3.putTile(tileC, 0);
            shelf3.putTile(tileF, 1);
            shelf3.putTile(tileB, 2);
            shelf3.putTile(tileT, 3);
            shelf3.putTile(tileP, 4);
        }
        assertFalse(pubObj_eight.getResultObjective(shelf3));
    }

    /**
     * Lambda function that see if there are five tiles of the same type in a diagonal
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_five_diagonal() throws ColumnAlreadyFullException, OutOfShelfException{
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, empty
        Tile tileC = new Tile(Type.CAT);
        Tile tileP = new Tile(Type.PLANT);
        Tile tileT = new Tile(Type.TROPHY);
        Tile tileB = new Tile(Type.BOOK);
        Tile tileF = new Tile(Type.FRAME);
        PublicObjective pubObj_diag = new PublicObjective("diag");

        //shelf1
        shelf1.putTile(tileC, 1);
        shelf1.putTile(tileC, 2);
        shelf1.putTile(tileC, 2);
        shelf1.putTile(tileC, 3);
        shelf1.putTile(tileC, 3);
        shelf1.putTile(tileC, 3);
        shelf1.putTile(tileC, 4);
        shelf1.putTile(tileC, 4);
        shelf1.putTile(tileC, 4);
        shelf1.putTile(tileC, 4);

        shelf1.putTile(tileP, 0);
        shelf1.putTile(tileP, 1);
        shelf1.putTile(tileP, 2);
        shelf1.putTile(tileP, 3);
        shelf1.putTile(tileP, 4);
        assertTrue(pubObj_diag.getResultObjective(shelf1));

        //shelf2
        shelf2.putTile(tileT, 0);
        shelf2.putTile(tileT, 0);
        shelf2.putTile(tileT, 0);
        shelf2.putTile(tileT, 0);
        shelf2.putTile(tileT, 1);
        shelf2.putTile(tileT, 1);
        shelf2.putTile(tileT, 1);
        shelf2.putTile(tileT, 2);
        shelf2.putTile(tileT, 2);
        shelf2.putTile(tileT, 3);

        shelf2.putTile(tileP, 0);
        shelf2.putTile(tileP, 1);
        shelf2.putTile(tileP, 2);
        shelf2.putTile(tileP, 3);
        shelf2.putTile(tileP, 4);

        shelf2.putTile(tileC, 1);
        shelf2.putTile(tileC, 2);
        shelf2.putTile(tileC, 2);
        shelf2.putTile(tileC, 3);
        shelf2.putTile(tileC, 3);
        shelf2.putTile(tileC, 3);
        shelf2.putTile(tileC, 4);
        shelf2.putTile(tileC, 4);
        shelf2.putTile(tileC, 4);
        shelf2.putTile(tileC, 4);
        assertTrue(pubObj_diag.getResultObjective(shelf2));

        //shelf3
        shelf3.putTile(tileT, 0);
        shelf3.putTile(tileT, 0);
        shelf3.putTile(tileT, 0);
        shelf3.putTile(tileT, 0);
        shelf3.putTile(tileT, 1);
        shelf3.putTile(tileT, 1);
        shelf3.putTile(tileT, 1);
        shelf3.putTile(tileT, 2);
        shelf3.putTile(tileT, 2);
        shelf3.putTile(tileT, 3);

        shelf3.putTile(tileC, 1);
        shelf3.putTile(tileC, 2);
        shelf3.putTile(tileC, 2);
        shelf3.putTile(tileC, 3);
        shelf3.putTile(tileC, 3);
        shelf3.putTile(tileC, 3);
        shelf3.putTile(tileC, 4);
        shelf3.putTile(tileC, 4);
        shelf3.putTile(tileC, 4);
        shelf3.putTile(tileC, 4);
        assertFalse(pubObj_diag.getResultObjective(shelf3));

        //shelf4
        assertFalse(pubObj_diag.getResultObjective(shelf4));

    }

    /**
     * Lambda function that see if there are at least two columns made of six different
     * type of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_two_diff_columns() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT);
        Tile tileB = new Tile(Type.PLANT);
        Tile tileC = new Tile(Type.TROPHY);
        Tile tileD = new Tile(Type.FRAME);
        Tile tileE = new Tile(Type.GAME);
        Tile tileF = new Tile(Type.BOOK);
        PublicObjective pubObj_diffCol = new PublicObjective("diffCol");

        //shelf1
        shelf1.putTile(tileA,0);
        shelf1.putTile(tileB,0);
        shelf1.putTile(tileC,0);
        shelf1.putTile(tileD,0);
        shelf1.putTile(tileE,0);
        shelf1.putTile(tileF,0);
        shelf1.putTile(tileA,1);
        shelf1.putTile(tileB,1);
        shelf1.putTile(tileC,1);
        shelf1.putTile(tileD,1);
        shelf1.putTile(tileE,1);
        shelf1.putTile(tileF,1);
        assertTrue(pubObj_diffCol.getResultObjective(shelf1));

        //shelf2
        shelf2.putTile(tileA,0);
        shelf2.putTile(tileB,0);
        shelf2.putTile(tileC,0);
        shelf2.putTile(tileD,0);
        shelf2.putTile(tileE,0);
        shelf2.putTile(tileF,0);
        shelf2.putTile(tileA,1);
        shelf2.putTile(tileB,1);
        shelf2.putTile(tileC,1);
        shelf2.putTile(tileD,1);
        shelf2.putTile(tileE,1);
        shelf2.putTile(tileF,1);
        for(int i=0; i<HEIGHT; i++){
            shelf2.putTile(tileA, 2);
            shelf2.putTile(tileB, 3);
            shelf2.putTile(tileC, 4);
        }
        assertTrue(pubObj_diffCol.getResultObjective(shelf2));

        //shelf3
        for(int i=0; i<HEIGHT-1; i++){
            shelf3.putTile(tileA, 0);
            shelf3.putTile(tileB, 1);
            shelf3.putTile(tileC, 2);
        }
        assertFalse(pubObj_diffCol.getResultObjective(shelf3));

        //shelf4
        shelf4.putTile(tileA,0);
        shelf4.putTile(tileB,0);
        shelf4.putTile(tileC,0);
        shelf4.putTile(tileD,0);
        shelf4.putTile(tileE,0);
        shelf4.putTile(tileE,0);
        shelf4.putTile(tileA,1);
        shelf4.putTile(tileB,1);
        shelf4.putTile(tileC,1);
        shelf4.putTile(tileD,1);
        shelf4.putTile(tileE,1);
        shelf4.putTile(tileE,1);
        assertFalse(pubObj_diffCol.getResultObjective(shelf4));

    }

    /**
     * Lambda function that see if there are at least two rows made of five different
     * type of tile
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_two_diff_rows() throws ColumnAlreadyFullException, OutOfShelfException {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT);
        Tile tileB = new Tile(Type.PLANT);
        Tile tileC = new Tile(Type.TROPHY);
        Tile tileD = new Tile(Type.FRAME);
        Tile tileE = new Tile(Type.GAME);
        Tile tileF = new Tile(Type.BOOK);
        PublicObjective pubObj_diffRow = new PublicObjective("diffRow");

        //shelf1
        for(int i=0; i<2; i++){
            shelf1.putTile(tileA, 0);
            shelf1.putTile(tileB, 1);
            shelf1.putTile(tileC, 2);
            shelf1.putTile(tileD, 3);
            shelf1.putTile(tileE, 4);
        }
        assertTrue(pubObj_diffRow.getResultObjective(shelf1));

        //shelf2
        for(int i=0; i<2; i++) {
            shelf2.putTile(tileA, 0);
            shelf2.putTile(tileB, 1);
            shelf2.putTile(tileC, 2);
            shelf2.putTile(tileD, 3);
            shelf2.putTile(tileE, 4);
        }
        for(int i=0; i<3; i++){
            shelf2.putTile(tileA, 0);
            shelf2.putTile(tileA, 1);
            shelf2.putTile(tileA, 2);
            shelf2.putTile(tileA, 3);
            shelf2.putTile(tileA, 4);
        }
        assertTrue(pubObj_diffRow.getResultObjective(shelf2));

        //shelf3
        for(int i=0; i<3; i++){
            shelf3.putTile(tileA, 0);
            shelf3.putTile(tileB, 1);
            shelf3.putTile(tileC, 2);
            shelf3.putTile(tileD, 3);
            shelf3.putTile(tileA, 4);
        }
        assertFalse(pubObj_diffRow.getResultObjective(shelf3));

        //shelf4
        for(int i=0; i<3; i++){
            shelf4.putTile(tileA, 0);
            shelf4.putTile(tileB, 1);
            shelf4.putTile(tileC, 2);
            shelf4.putTile(tileD, 3);
            shelf4.putTile(tileA, 4);
        }
        shelf4.putTile(tileA, 0);
        shelf4.putTile(tileB, 1);
        shelf4.putTile(tileC, 2);
        shelf4.putTile(tileD, 3);
        assertFalse(pubObj_diffRow.getResultObjective(shelf4));

    }


    private static void checkAdjacent(Optional<Tile>[][] matrix, boolean[][] visited, int i, int j, Tile value) {
        visited[i][j] = true;

        if (i > 0 && matrix[i-1][j].isPresent() && matrix[i-1][j].get().equals(value) && !visited[i-1][j]) {
            checkAdjacent(matrix, visited, i-1, j, value);
        }

        if (i < 6-1 && matrix[i-1][j].isPresent() && matrix[i+1][j].get().equals(value) && !visited[i+1][j]) {
            checkAdjacent(matrix, visited, i+1, j, value);
        }

        if (j > 0 && matrix[i-1][j].isPresent() && matrix[i][j-1].get().equals(value) && !visited[i][j-1]) {
            checkAdjacent(matrix, visited, i, j-1, value);
        }

        if (j < 5-1 && matrix[i-1][j].isPresent() && matrix[i][j+1].get().equals(value) && !visited[i][j+1]) {
            checkAdjacent(matrix, visited, i, j+1, value);
        }
    }

}
