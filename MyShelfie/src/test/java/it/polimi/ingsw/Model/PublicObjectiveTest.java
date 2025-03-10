package it.polimi.ingsw.Model;

import it.polimi.ingsw.Enumeration.PubObjType;
import it.polimi.ingsw.Enumeration.Type;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the {@link PublicObjective} methods.
 */
public class PublicObjectiveTest {

    private final int WIDTH = 5;
    private final int HEIGHT = 6;

    @Before
    public void setUp(){
    }

    /**
     * Testing of the lambda function that see if there's a "cross"
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_cross() {
        Shelf shelf1 = new Shelf(); // true, elementary
        Shelf shelf2 = new Shelf(); // true, not elementary
        Shelf shelf3 = new Shelf(); // false, not elementary
        Shelf shelf4 = new Shelf(); // false, no tiles
        Tile tileC = new Tile(Type.CAT, 1);
        Tile tileP = new Tile(Type.PLANT, 1);
        Tile tileT = new Tile(Type.TROPHY, 1);
        PublicObjective pubObj_cross = new PublicObjective(PubObjType.CROSS);

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
     * Lambda function that sees if there is a "stair" on the shelf
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_stair() {
        Shelf shelf1 = new Shelf(); // true, LR
        Shelf shelf2 = new Shelf(); // true, RL
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT, 1);
        Tile tileB = new Tile(Type.PLANT, 1);
        Tile tileC = new Tile(Type.TROPHY, 1);
        PublicObjective pubObj = new PublicObjective(PubObjType.STAIR);

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

    /**
     * Lambda function that sees if there are six couples of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_six_couples() {
        Shelf shelf1 = new Shelf(); // true, easy
        Tile tileA = new Tile(Type.CAT, 1);
        Tile tileB = new Tile(Type.PLANT, 1);
        Tile tileC = new Tile(Type.TROPHY, 1);
        Tile tileD = new Tile(Type.BOOK, 1);
        Tile tileE = new Tile(Type.FRAME, 1);
        Tile tileF = new Tile(Type.GAME, 1);

        PublicObjective pubObj_sixCouples = new PublicObjective(PubObjType.SIX_COUPLES);

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

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                if(shelf1.getShelf()[i][j].isPresent()) {
                    switch (shelf1.getShelf()[i][j].get().getType()) {
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

        assertTrue(pubObj_sixCouples.getResultObjective(shelf1));
    }

    /**
     * Lambda function that sees if there are four quadruples of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_four_quadruple() {
        Shelf shelf1 = new Shelf(); // true, easy
        Tile tileA = new Tile(Type.CAT, 1);
        Tile tileB = new Tile(Type.PLANT, 1);
        Tile tileC = new Tile(Type.TROPHY, 1);
        Tile tileD = new Tile(Type.BOOK, 1);
        Tile tileE = new Tile(Type.FRAME, 1);
        Tile tileF = new Tile(Type.GAME, 1);

        PublicObjective pubObj = new PublicObjective(PubObjType.FOUR_QUADRUPLES);

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

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                if(shelf1.getShelf()[i][j].isPresent()) {
                    switch (shelf1.getShelf()[i][j].get().getType()) {
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

        assertTrue(pubObj.getResultObjective(shelf1));
    }

    /**
     * Lambda function that assures that all four angles have the same type of tile
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_four_angles() {
        Shelf shelf1 = new Shelf(); // true
        Shelf shelf2 = new Shelf(); // false, wrong
        Shelf shelf3 = new Shelf(); // false, missing
        Tile tileC = new Tile(Type.CAT, 1);
        Tile tileP = new Tile(Type.PLANT, 1);
        PublicObjective pubObj_angles = new PublicObjective(PubObjType.ANGLES);

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
     * Lambda function that sees if there are two squares of the same type of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_two_squares() {
        Shelf shelf1 = new Shelf(); // false, easy
        Shelf shelf2 = new Shelf(); // true, easy
        Shelf shelf3 = new Shelf(); // true, hard
        Shelf shelf4 = new Shelf(); // false
        Tile tileC = new Tile(Type.CAT, 1);
        Tile tileP = new Tile(Type.PLANT, 1);
        Tile tileT = new Tile(Type.TROPHY, 1);
        PublicObjective pubObj_squares = new PublicObjective(PubObjType.TWO_SQUARES);

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
     * Lambda function that sees if there are three full columns with max 3 different types of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_three_similar_columns() {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT, 1);
        Tile tileB = new Tile(Type.PLANT, 1);
        Tile tileC = new Tile(Type.TROPHY, 1);
        Tile tileD = new Tile(Type.BOOK, 1);
        Tile tileE = new Tile(Type.FRAME, 1);
        Tile tileF = new Tile(Type.GAME, 1);
        PublicObjective pubObj_threeCol = new PublicObjective(PubObjType.COL_THREE_TYPES);

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
     * Lambda function that sees if there are four full rows with max 3 different types of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_four_similar_rows() {
        Shelf shelf1 = new Shelf(); // true
        Shelf shelf2 = new Shelf(); // false
        Tile tileA = new Tile(Type.CAT, 1);
        Tile tileB = new Tile(Type.PLANT, 1);
        Tile tileC = new Tile(Type.TROPHY, 1);
        Tile tileD = new Tile(Type.BOOK, 1);
        Tile tileE = new Tile(Type.FRAME, 1);
        Tile tileF = new Tile(Type.GAME, 1);
        PublicObjective pubObj_fourRows = new PublicObjective(PubObjType.ROW_THREE_TYPES);

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
     * Lambda function that sees if there are eight tiles of the same type
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_eight_same() {
        int k;
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false
        Tile tileC = new Tile(Type.CAT, 1);
        Tile tileP = new Tile(Type.PLANT, 1);
        Tile tileT = new Tile(Type.TROPHY, 1);
        Tile tileB = new Tile(Type.BOOK, 1);
        Tile tileF = new Tile(Type.FRAME, 1);
        PublicObjective pubObj_eight = new PublicObjective(PubObjType.EIGHT);

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
     * Lambda function that sees if there are five tiles of the same type in a diagonal
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_five_diagonal() {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, empty
        Tile tileC = new Tile(Type.CAT,1);
        Tile tileP = new Tile(Type.PLANT,1);
        Tile tileT = new Tile(Type.TROPHY,1);
        Tile tileB = new Tile(Type.BOOK,1);
        Tile tileF = new Tile(Type.FRAME,1);
        PublicObjective pubObj_diag = new PublicObjective(PubObjType.DIAG);

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
     * Lambda function that sees if there are at least two columns made of six different types of tiles
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_two_diff_columns() {
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT,1);
        Tile tileB = new Tile(Type.PLANT,1);
        Tile tileC = new Tile(Type.TROPHY,1);
        Tile tileD = new Tile(Type.FRAME,1);
        Tile tileE = new Tile(Type.GAME,1);
        Tile tileF = new Tile(Type.BOOK,1);
        PublicObjective pubObj_diffCol = new PublicObjective(PubObjType.DIFF_COL);

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
     * Lambda function that sees if there are at least two rows made of five different types of tile
     * @author Pierantonio Mauro
     */
    @Test
    public void lambda_two_diff_rows(){
        Shelf shelf1 = new Shelf(); // true, easy
        Shelf shelf2 = new Shelf(); // true, hard
        Shelf shelf3 = new Shelf(); // false, easy
        Shelf shelf4 = new Shelf(); // false, hard
        Tile tileA = new Tile(Type.CAT,1);
        Tile tileB = new Tile(Type.PLANT,1);
        Tile tileC = new Tile(Type.TROPHY,1);
        Tile tileD = new Tile(Type.FRAME,1);
        Tile tileE = new Tile(Type.GAME,1);
        Tile tileF = new Tile(Type.BOOK,1);
        PublicObjective pubObj_diffRow = new PublicObjective(PubObjType.DIFF_ROW);

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

}
