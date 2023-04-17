package it.polimi.ingsw;


import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import exceptions.PublicObjectiveAlreadyCompletedException;

import java.util.*;

/**
 * Support class for the method adjacent tiles
 * @author Guido Gonnella
 */
 class coord{
    int x, y;
    public coord(int a, int b){
        this.x = a;
        this.y = b;
    }
}

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
    private PrivateObjective privateObjective;
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
        this.privateObjective = null;
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
        //if(points > 0) this.playerPoints += points;
        //perché se >0? La prima volta points sarà sempre = 0 quindi non aggiunge mai
        this.playerPoints += points;
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

    /**
     * <h1>Adjacent Points</h1>
     * It uses the breadth first search algorithm for graphs,
     * it uses a queue to memorize and then check the spot and its surroundings.<br>
     * Every spot in the queue is the same type, is the boundaries of the shelf, and it has not been already checked.<br>
     * If a spot in the shelf meets these requirements it is put in the queue, checks in the checked matrix, and the adjacent counter is incremented.<br><br>
     * When the queue is empty, which means there aren't any other tiles to check, and based on the adjacent counter, the points variable is assigned: <br>
     * 2 points if there are 3 adjacent tiles<br>
     * 3 points if there are 4 adjacent tiles<br>
     * 5 points if there are 5 adjacent tiles<br>
     * 8 points if there are more than 5 adjacent tiles.
     *
     * @param pshelf the player's shelf
     * @param checked a matrix, equally sized to the shelf, used for checking if a certain spot on the shelf has been already checked
     * @param i starting point from where to check
     * @param j starting point from where to check
     * @return Points based on the amount of equal type adjacent tiles
     * @throws NoSuchElementException It throws this exception if the method Optional.get() is called on an empty Optional,
     *                                  but checking if it is present, it may prevent from throwing this exception
     * @author Guido Gonnella
     */
    private int adjacentPoints(Shelf pshelf, boolean[][] checked, int i, int j) throws NoSuchElementException {
        int points = 0, adjacent = 1;
        Optional<Tile> shelfie[][] = pshelf.getShelf();
        Queue<coord> q = new ArrayDeque<>();

        q.add(new coord(i, j));
        checked[i][j] = true;

        while(!q.isEmpty()){
            int x = q.peek().x, y = q.peek().y;
            q.poll();

            // tile under the current tile
            if((!shelfie[x+1][y].isPresent() || shelfie[x+1][y].get().getType().equals(shelfie[x][y].get().getType())) && x+1 >= 0 && x+1 < 6 && y >= 0 && y < 5 && !checked[x+1][y]){
                checked[x+1][y] = true;
                adjacent++;
                q.add(new coord(x+1, y));
            }

            // tile over the current tile
            if((!shelfie[x-1][y].isPresent() || shelfie[x-1][y].get().getType().equals(shelfie[x][y].get().getType())) && x-1 >= 0 && x-1 < 6 && y >= 0 && y < 5 && !checked[x-1][y]){
                checked[x-1][y] = true;
                adjacent++;
                q.add(new coord(x-1, y));
            }

            // tile on the right of the current tile
            if((!shelfie[x][y+1].isPresent() || shelfie[x][y+1].get().getType().equals(shelfie[x][y].get().getType())) && x >= 0 && x < 6 && y+1 >= 0 && y+1 < 5 && !checked[x][y+1]){
                checked[x][y+1] = true;
                adjacent++;
                q.add(new coord(x, y+1));
            }

            // tile on the left of the current tile
            if((!shelfie[x][y-1].isPresent() || shelfie[x][y-1].get().getType().equals(shelfie[x][y].get().getType())) && x >= 0 && x < 6 && y-1 >= 0 && y-1 < 5 && !checked[x][y-1]){
                checked[x][y-1] = true;
                adjacent++;
                q.add(new coord(x, y-1));
            }

        }


        //assignement to the point variable the points based on how many equal type adjacent tile there are
        if(adjacent < 3) points = 0;
        else if(adjacent == 3) points = 2;
        else if(adjacent == 4) points = 3;
        else if(adjacent == 5) points = 5;
        else if(adjacent >  6) points = 8;

        return points;
    }


    /**
     * An array with the Points for the private objective of the player
     */
    private final int PRVPOINTS[] = {0, 1, 2, 4, 6, 9, 12};

    /**
     * <h1>CountPoints</h1>
     *
     * It uses the shelf variable to calculate the points, from the private objective and the number of the adjacent tiles.<br><br>
     * The first part counts how many elements in the private objective is on the player's shelf, then using the counter as a
     * index, from the {@link #PRVPOINTS PRVPOINTS} array get the corresponding points.<br><br>
     * The {@link #adjacentPoints(Shelf, boolean[][], int, int) adjacentPoints} function returns the points based on how many equal adjacent tiles there are on the shelf.
     *
     * @author Guido Gonnella
     */
    public void countPoints(){
        Shelf temp = shelf;
        int points = 0;

        //Adding points from completion of the private object
        int numTiles = 0;
        for(ElementObjective e : privateObjective.getObjective()){
            if(!shelf.getShelf()[e.getX()][e.getY()].isPresent() || shelf.getShelf()[e.getX()][e.getY()].get().getType().equals(e.getType())){
                numTiles++;
            }
        }

        points += PRVPOINTS[numTiles];

        //Adding points from the adjacent tiles
        boolean checked[][] = new boolean[6][5];
        Arrays.fill(checked, false);

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(!checked[i][j] && temp.getShelf()[i][j].isPresent()) points += adjacentPoints(temp, checked, i, j);
            }
        }

        //adding the points to the player's points attribute
        addPoints(points);
    }


    /**
     * Update the flag for a specific objective as true, meaning the player has completed that objective
     *
     * @param obj index of the two boolean array
     * @exception PublicObjectiveAlreadyCompletedException when an objective is already completed by the player(<code>this</code>)
     * @author Guido Gonnella
     */
    public void updatePubObjFlag(int obj) throws PublicObjectiveAlreadyCompletedException{
        if(!this.pubObjFlag[obj]){ this.pubObjFlag[obj] = true;}
        else throw new PublicObjectiveAlreadyCompletedException("Objective n° " + obj + " already completed\n");
    }

    /**
     * Getter method of the attribute nickname
     * @return return the nickname of the player
     * @author Samuele Mazzoleni
     * @author Guido Gonnella
     */
    public String getNickname() {
        return this.nickname;
    }

    public boolean[] getPubObjFlag() {
        return this.pubObjFlag;
    }

    /**
     * temporary method, will be replaced by updatePubOnjFlag
     * when the exception will be handled
     * @param index of the public objective
     * @author Pierantonio Mauro
     */
    public void setPubObjFlag(int index){
        this.pubObjFlag[index] = true;
    }

    public void setPrivateObjective(PrivateObjective privateObjective){
        this.privateObjective = privateObjective;
    }
}
