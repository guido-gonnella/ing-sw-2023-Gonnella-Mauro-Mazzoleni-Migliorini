package it.polimi.ingsw.Model;

import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import it.polimi.ingsw.Model.Enumeration.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
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
        CommonObj cross = (shelf) -> {
            int i,j;
            int contCross = 0;
            Optional<Tile>[][] tempShelf = shelf.getShelf();

            for(i=1; i<5 && contCross==0; i++){
                for(j=1; j<4 && contCross==0; j++){
                    if(tempShelf[i][j].isPresent()){
                        Type tempType = tempShelf[i][j].get().getType();
                        if(tempShelf[i-1][j-1].isPresent() &&
                           tempShelf[i+1][j+1].isPresent() &&
                           tempShelf[i+1][j-1].isPresent() &&
                           tempShelf[i-1][j+1].isPresent()){
                            if(tempType.equals(tempShelf[i-1][j-1].get().getType()) &&
                               tempType.equals(tempShelf[i+1][j+1].get().getType()) &&
                               tempType.equals(tempShelf[i+1][j-1].get().getType()) &&
                               tempType.equals(tempShelf[i-1][j+1].get().getType())){contCross = 1;}
                        }
                    }
                }
            }
            if(contCross==0)
                return false;
            else
                return true;
        };
        PublicObjective pubObj_cross = new PublicObjective(cross);

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

    @Test
    public void lambda_six_couples(){

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
        CommonObj stair = (shelf) -> {
            Optional<Tile>[][] temp = shelf.getShelf();
            int heights[] = new int[5];

            for(int i=0; i<5; i++){
                heights[i] = 0;
                for(int j=5; j>=0; j--){
                    if(temp[j][i].isPresent())
                        heights[i]++;
                }
            }

            boolean stairLR = true;
            boolean stairRL = true;
            for(int i=0; i<4; i++){
                if(heights[0] >= 1 && heights[4] >= 1) {
                    if(heights[i] + 1 != heights[i+1])
                        stairLR = false;
                    if(heights[i] != heights[i+1] + 1)
                        stairRL = false;
                }
                else
                    return false;
            }

            return stairRL || stairLR;
        };
        PublicObjective pubObj = new PublicObjective(stair);

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

    @Test
    public void lambda_four_quadruple() throws ColumnAlreadyFullException, OutOfShelfException {

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
        CommonObj angles = (shelf) -> {
            Optional<Tile>[][] temp = shelf.getShelf();

            if (temp[0][0].isPresent() && temp[HEIGHT-1][0].isPresent() &&
                temp[0][WIDTH-1].isPresent() & temp[HEIGHT-1][WIDTH-1].isPresent()){
                if     (temp[0][0].get().getType().equals(temp[HEIGHT-1][0].get().getType()) &&
                        temp[0][0].get().getType().equals(temp[0][WIDTH-1].get().getType()) &&
                        temp[0][0].get().getType().equals(temp[HEIGHT-1][WIDTH-1].get().getType())){
                    return true;
                }
            }
            return false;
        };
        PublicObjective pubObj_angles = new PublicObjective(angles);

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
        CommonObj twoSquares = (shelf) -> {
            Optional<Tile>[][] temp = shelf.getShelf();
            boolean[][] check = new boolean[6][5];
            int countSquaresCat = 0;
            int countSquaresPlant = 0;
            int countSquaresTrophy = 0;
            int countSquaresFrame = 0;
            int countSquaresGame = 0;
            int countSquaresBook = 0;

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    check[i][j] = false;
                }
            }

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    if (temp[i][j].isPresent() && temp[i+1][j].isPresent() &&
                        temp[i][j+1].isPresent() && temp[i+1][j+1].isPresent()) {
                        if (temp[i][j].get().getType().equals(temp[i+1][j].get().getType()) &&
                            temp[i][j].get().getType().equals(temp[i+1][j+1].get().getType()) &&
                            temp[i][j].get().getType().equals(temp[i][j+1].get().getType()) &&
                            !check[i][j] && !check[i+1][j] && !check[i][j+1] && !check[i+1][j+1]) {
                            switch (temp[i][j].get().getType()) {
                                case CAT -> countSquaresCat++;
                                case BOOK -> countSquaresBook++;
                                case GAME -> countSquaresGame++;
                                case FRAME -> countSquaresFrame++;
                                case PLANT -> countSquaresPlant++;
                                case TROPHY -> countSquaresTrophy++;
                                default -> {
                                }
                            }
                            check[i][j] = true;
                            check[i+1][j] = true;
                            check[i][j+1] = true;
                            check[i+1][j+1] = true;
                        }
                    }
                }
            }

            if (countSquaresCat >= 2 || countSquaresBook >= 2 || countSquaresFrame >= 2 ||
                countSquaresPlant >= 2 || countSquaresGame >= 2 || countSquaresTrophy >= 2) return true;
            return false;
        };
        PublicObjective pubObj_squares = new PublicObjective(twoSquares);

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
        CommonObj colThreeTypes = (shelf) -> {
            int col,rig;
            int flag;
            Optional<Tile>[][] tempShelf = shelf.getShelf();
            int contCol = 0;
            int[] contType = new int[6];
            int contDiffTypes;

            for(col=0; col<5; col++){
                flag = 1;
                contDiffTypes = 0;
                for(int i=0; i<6; i++)
                    contType[i] = 0;

                for(rig=0; rig<6 && flag==1; rig++){
                    if(tempShelf[rig][col].isEmpty())
                        flag = 0;
                    else{
                        switch (tempShelf[rig][col].get().getType()) {
                            case CAT -> contType[0]++;
                            case BOOK -> contType[1]++;
                            case GAME -> contType[2]++;
                            case FRAME -> contType[3]++;
                            case PLANT -> contType[4]++;
                            case TROPHY -> contType[5]++;
                            default -> {
                            }
                        }
                    }
                }
                if(flag == 1){
                    for(int i=0; i<6; i++){
                        if(contType[i]>0)
                            contDiffTypes++;
                    }
                    if(contDiffTypes > 0 && contDiffTypes <= 3)
                        contCol++;

                }
            }
            if(contCol>=3)
                return true;
            else
                return false;
        };
        PublicObjective pubObj_threeCol = new PublicObjective(colThreeTypes);

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
        CommonObj rowThreeTypes = (shelf) -> {
            int col,rig;
            int flag;
            Optional<Tile>[][] tempShelf = shelf.getShelf();
            int contRow = 0;
            int[] contType = new int[6];
            int contDiffTypes;

            for(rig=0; rig<6; rig++){
                flag = 1;
                contDiffTypes = 0;
                for(int i=0; i<6; i++)
                    contType[i] = 0;

                for(col=0; col<5 && flag==1; col++){
                    if(tempShelf[rig][col].isEmpty())
                        flag = 0;
                    else{
                        switch (tempShelf[rig][col].get().getType()) {
                            case CAT -> contType[0]++;
                            case BOOK -> contType[1]++;
                            case GAME -> contType[2]++;
                            case FRAME -> contType[3]++;
                            case PLANT -> contType[4]++;
                            case TROPHY -> contType[5]++;
                            default -> {
                            }
                        }
                    }
                }
                if(flag == 1){
                    for(int i=0; i<6; i++){
                        if(contType[i]>0)
                            contDiffTypes++;
                    }
                    if(contDiffTypes > 0 && contDiffTypes <= 3)
                        contRow++;

                }
            }
            if(contRow>=4)
                return true;
            else
                return false;
        };
        PublicObjective pubObj_fourRows = new PublicObjective(rowThreeTypes);

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
        CommonObj eight = (shelf) -> {
            int i,j;
            int contCat = 0;
            int contFrame = 0;
            int contGame = 0;
            int contPlant = 0;
            int contTrophy = 0;
            int contBook = 0;
            Optional<Tile>[][] tempShelf = shelf.getShelf();

            for(i=0; i<6; i++){
                for(j=0; j<5; j++){
                    if(tempShelf[i][j].isPresent()){
                        switch (tempShelf[i][j].get().getType()) {
                            case CAT -> contCat++;
                            case BOOK -> contBook++;
                            case GAME -> contGame++;
                            case FRAME -> contFrame++;
                            case PLANT -> contPlant++;
                            case TROPHY -> contTrophy++;
                            default -> {
                            }
                        }
                    }
                }
            }

            if(contCat>=8 || contBook>=8 || contGame>=8 ||
                    contFrame>=8 || contPlant>=8 || contTrophy>=8)
                return true;
            else
                return false;
        };
        PublicObjective pubObj_eight = new PublicObjective(eight);

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
        CommonObj diag = (shelf) -> {
            int i=0;
            int contDiag = 0;
            int k=0;
            Optional<Tile>[][] tempShelf = shelf.getShelf();

            while(i<2 && contDiag==0){
                if (tempShelf[i][0].isPresent()){
                    Type tempType = tempShelf[i][0].get().getType();
                    int flag = 1;
                    for (k = 1; k < 5 && flag == 1; k++) {
                        if (tempShelf[i + k][k].isEmpty())
                            flag = 0;
                        else if (tempType != tempShelf[i + k][k].get().getType()){
                            flag = 0;
                        }
                    }
                    if(flag==1)
                        contDiag=1;
                }
                i++;
            }

            i=0;
            while(i<2 && contDiag==0){
                if (tempShelf[i+4][0].isPresent()) {
                    Type tempType = tempShelf[i+4][0].get().getType();
                    int flag = 1;
                    for (k = 1; k < 5 && flag == 1; k++) {
                        if (tempShelf[i + 4 - k][k].isEmpty())
                            flag = 0;
                        else if (tempType != tempShelf[i + 4 - k][k].get().getType()){
                            flag = 0;
                        }
                    }
                    if(flag==1)
                        contDiag=1;
                }
                i++;
            }

            if(contDiag==1)
                return true;
            else
                return false;
        };
        PublicObjective pubObj_diag = new PublicObjective(diag);

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
        CommonObj diffCol = (shelf) -> {
            int col,rig;
            Optional<Tile>[][] tempShelf = shelf.getShelf();
            int contCol = 0;

            for(col=0; col<5; col++){
                int flag = 1;
                int contCat = 0;
                int contFrame = 0;
                int contGame = 0;
                int contPlant = 0;
                int contTrophy = 0;
                int contBook = 0;
                for(rig=0; rig<6 && flag==1; rig++){
                    if(tempShelf[rig][col].isEmpty())
                        flag = 0;
                    else{
                        switch (tempShelf[rig][col].get().getType()) {
                            case CAT -> contCat++;
                            case BOOK -> contBook++;
                            case GAME -> contGame++;
                            case FRAME -> contFrame++;
                            case PLANT -> contPlant++;
                            case TROPHY -> contTrophy++;
                            default -> {
                            }
                        }
                    }
                }
                if(flag==1 && contCat<=1 && contBook<=1 && contFrame<=1 &&
                              contGame<=1 && contPlant<=1 && contTrophy<=1)
                    contCol++;
            }

            if(contCol>=2)
                return true;
            else
                return false;
        };
        PublicObjective pubObj_diffCol = new PublicObjective(diffCol);

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
        CommonObj diffRow = (shelf) -> {
            int col,rig;
            Optional<Tile>[][] tempShelf = shelf.getShelf();
            int contRow = 0;

            for(rig=0; rig<6; rig++){
                int flag = 1;
                int contCat = 0;
                int contFrame = 0;
                int contGame = 0;
                int contPlant = 0;
                int contTrophy = 0;
                int contBook = 0;
                for(col=0; col<5 && flag==1; col++){
                    if(tempShelf[rig][col].isEmpty())
                        flag = 0;
                    else{
                        switch (tempShelf[rig][col].get().getType()) {
                            case CAT -> contCat++;
                            case BOOK -> contBook++;
                            case GAME -> contGame++;
                            case FRAME -> contFrame++;
                            case PLANT -> contPlant++;
                            case TROPHY -> contTrophy++;
                            default -> {
                            }
                        }
                    }
                }
                if(flag==1 && contCat<=1 && contBook<=1 && contFrame<=1
                        && contGame<=1 && contPlant<=1 && contTrophy<=1)
                    contRow++;
            }

            if(contRow>=2)
                return true;
            else
                return false;
        };
        PublicObjective pubObj_diffRow = new PublicObjective(diffRow);

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
