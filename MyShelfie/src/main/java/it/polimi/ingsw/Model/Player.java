package it.polimi.ingsw.Model;


import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Class that describes the player, it contains the nickname, the player's point, the player's shelf, the player private objective
 * and two flags that points out whether the player has done the public objective
 * The class has the methods to put the tile in a certain column, to modify the flags, to add points to the actual points
 * @author Guido Gonnella
 */
public class Player {
    private final String nickname;
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
        this.privateObjective = null;
    }

    /**
     *
     * @param tile Tile object to insert in the shelf
     * @param col column number where to insert the tile
     * @author Guido Gonnella
     */
    public void placeTile(Tile tile, int col){
            shelf.putTile(tile, col);
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
    public Shelf getShelf() {
        return this.shelf;
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
     * @return Points based on the number of equal type adjacent tiles
     * @throws NoSuchElementException It throws this exception if the method Optional.get() is called on an empty Optional,
     *                                  but checking if it is present, it may prevent from throwing this exception
     * @author Guido Gonnella
     */
    private int adjacentPoints(Shelf pshelf, boolean[][] checked, int i, int j) throws NoSuchElementException {
        int points = 0, adjacent = 1;
        SerializableOptional<Tile>[][] shelf = pshelf.getShelf();
        Queue<Coords> q = new ArrayDeque<>();

        q.add(new Coords(i, j));
        checked[i][j] = true;

        while(!q.isEmpty()){
            int x = q.peek().ROW, y = q.peek().COL;
            q.poll();

            // tile under the current tile
            if(x+1 >= 0 && x+1 < 6 && y >= 0 && y < 5 && !checked[x+1][y] && (shelf[x+1][y].isPresent() && shelf[x+1][y].get().getType().equals(shelf[x][y].get().getType())) ){
                checked[x+1][y] = true;
                adjacent++;
                q.add(new Coords(x+1, y));
            }

            // tile over the current tile
            if( x-1 >= 0 && x-1 < 6 && y >= 0 && y < 5 && !checked[x-1][y] &&(shelf[x-1][y].isPresent() && shelf[x-1][y].get().getType().equals(shelf[x][y].get().getType())) ){
                checked[x-1][y] = true;
                adjacent++;
                q.add(new Coords(x-1, y));
            }

            // tile on the right of the current tile
            if(x >= 0 && x < 6 && y+1 >= 0 && y+1 < 5 && !checked[x][y+1] && (shelf[x][y+1].isPresent() && shelf[x][y+1].get().getType().equals(shelf[x][y].get().getType())) ){
                checked[x][y+1] = true;
                adjacent++;
                q.add(new Coords(x, y+1));
            }

            // tile on the left of the current tile
            if(x >= 0 && x < 6 && y-1 >= 0 && y-1 < 5 && !checked[x][y-1] &&(shelf[x][y-1].isPresent() && shelf[x][y-1].get().getType().equals(shelf[x][y].get().getType()))){
                checked[x][y-1] = true;
                adjacent++;
                q.add(new Coords(x, y-1));
            }

        }


        //assignment to the point variable the points based on how many equal type adjacent tile there are
        if(adjacent < 3) points = 0;
        else if(adjacent == 3) points = 2;
        else if(adjacent == 4) points = 3;
        else if(adjacent == 5) points = 5;
        else if(adjacent >  5) points = 8;

        return points;
    }


    /**
     * An array with the Points for the private objective of the player
     */
    private final int[] PRVPOINTS = {0, 1, 2, 4, 6, 9, 12};

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
            if(shelf.getShelf()[e.getROW()][e.getCOL()].isPresent() && shelf.getShelf()[e.getROW()][e.getCOL()].get().getType().equals(e.getType())){
                numTiles++;
            }
        }

        points += PRVPOINTS[numTiles];

        //Adding points from the adjacent tiles
        boolean[][] checked = new boolean[6][5];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                checked[i][j] = false;
            }
        }

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++){
                if(!checked[i][j] && temp.getShelf()[i][j].isPresent())
                    points += adjacentPoints(temp, checked, i, j);
            }
        }

        //adding the points to the player points' attribute
        addPoints(points);
    }


    /**
     * Update the flag for a specific objective as true, meaning the player has completed that objective
     *
     * @param obj index of the two boolean arrays
     * @author Guido Gonnella
     */
    public void updatePubObjFlag(int obj) {
        if(!this.pubObjFlag[obj]){ this.pubObjFlag[obj] = true;}
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

    /**
     * Return the array containing the flags which represent if the corresponding {@link PublicObjective} has been reached by the player.
     * @return the array with the booleans.
     */
    public boolean[] getPubObjFlag() {
        return this.pubObjFlag;
    }

    /**
     * Setter for the player's {@link PrivateObjective}
     * @param privateObjective the privateObjective
     */
    public void setPrivateObjective(PrivateObjective privateObjective){
        this.privateObjective = privateObjective;
    }

    /**
     * Getter for the player's {@link PrivateObjective}
     * @return the player's privateObjective
     */
    public PrivateObjective getPrivateObjective() {
        return this.privateObjective;
    }

    /**
     * Getter for the player's actual points.
     * @return
     */
    public int getPlayerPoints() {
        return playerPoints;
    }

    /**
     * Return the number of free spaces in the shelf given a column index
     * @param col the column
     * @return the number of free spaces
     */
    public int getFreeSpaceInCol(int col){
        int free = 0;

        for(int i = 0; i < 6 && shelf.getShelf()[i][col].isEmpty(); i ++){
            if(shelf.getShelf()[i][col].isEmpty()) free++;
        }

        return free;
    }
}
