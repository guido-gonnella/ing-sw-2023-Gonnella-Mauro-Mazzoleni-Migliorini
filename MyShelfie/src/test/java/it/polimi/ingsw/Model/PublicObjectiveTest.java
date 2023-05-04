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

    @Test
    public void lambda_stair(){

    }

    @Test
    public void lambda_four_quadruple() throws ColumnAlreadyFullException, OutOfShelfException {

    }

    /**
     * Lambda function that assures that all four angles have the same type of tile
     * @author Pierantonio Mauro
     * STATE: DONE, WORKING
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

    @Test
    public void lambda_two_squares(){

    }

    @Test
    public void lambda_three_diff_columns(){

    }

    @Test
    public void lambda_four_diff_rows(){

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

    @Test
    public void lambda_two_same_columns(){

    }

    @Test
    public void lambda_two_same_rows(){

    }




}
