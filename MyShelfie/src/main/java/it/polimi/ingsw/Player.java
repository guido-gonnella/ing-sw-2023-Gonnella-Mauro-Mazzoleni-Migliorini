package it.polimi.ingsw;


import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import exceptions.PublicObjectiveAlreadyCompletedException;

/**
 * Class that describes the player, it contains the nickname, the player's point, the player's shelf, the player private objective
 * and two flags that points out whether the player has done the public objective
 * The class has the methods to put the tile in a certain column, to modify the flags, to add points to the actual points
 * @author Guido Gonnella
 */
public class Player {
    private String nickname;
    private int playerPoints;
    private Shelf shelf;
    //private PrivateObjective privateObjective;
    private boolean[] pubObjFlag;

    /**
     * @param nickname the nickname of the player
     * @author Guido Gonnella
     */
    public Player(String nickname){
        this.nickname = nickname;
        this.shelf = new Shelf();
        this.playerPoints = 0;
        this.pubObjFlag = new boolean[2];
        pubObjFlag[0] = pubObjFlag[1] = false;

        //this.privateObjective = null;
    }

    /**
     *
     * @param tile Tile object to insert in the shelf
     * @param col column number where to insert the tile
     * @author Guido Gonnella
     */
    public void placeTile(Tile tile, int col){
        try{
            shelf.putTile(tile, col);
        }catch(ColumnAlreadyFullException e1) {
            System.out.println(e1);
        }catch(OutOfShelfException e2){
            System.out.println(e2);
        }
    }

    /**
     *
     * @param points number of points to add
     * @author Guido Gonnella
     */
    public void addPoints(int points){
        if(points > 0) this.playerPoints += points;
    }

    /**
     *
     * @return returns a copy of the player's shelf
     * @implNote The method does not return the actual player's shelf, because then can be modified by anyone and cause some error
     * @author Guido Gonnella
     */
    public Shelf getShelf(){
        Shelf temp = this.shelf;
        return temp;
    }

    private int adjacentPoints(Shelf shelf){
        int points = 0;



        return points;
    }

    public void countPoints(){
        Shelf temp = shelf;
        //Adding points from completion of the private object

        //Adding points from the adjacent tiles

    }


    /**
     * Update the flag for a specific objective as true, meaning the player has completed that objective
     *
     * @param obj index of the two boolean array
     * @exception PublicObjectiveAlreadyCompletedException when an objective is already completed by the player(<code>this</code>)
     * @author Guido Gonnella
     */
    public void updatePubObjFlag(int obj) throws PublicObjectiveAlreadyCompletedException{
        if(!pubObjFlag[obj]) pubObjFlag[obj] = true;
        else throw new PublicObjectiveAlreadyCompletedException("Objective n° " + obj + " already completed\n");
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean[] getPubObjFlag() {
        return this.pubObjFlag;
    }
}
