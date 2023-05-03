package it.polimi.ingsw.Model;

import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
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

    @Test
    public void lambda_cross(){

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
     * Testing of the lambda function that assures that all four angles have the same type of tile
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

    @Test
    public void lambda_eight_same(){

    }

    @Test
    public void lambda_five_diagonal(){

    }

    @Test
    public void lambda_two_same_columns(){

    }

    @Test
    public void lambda_two_same_rows(){

    }




}
