package it.polimi.ingsw.Model;

import it.polimi.ingsw.Enumeration.PubObjType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that describes the game, all of its most important methods and attributes
 * It contains all the data and information necessary to permit the game to be saved and resumed if interrupted
 * This class is also used to initialize all the components at the very beginning of every game and keeping them updated
 *
 * @author Samuele Mazzoleni
 */

public class Game implements Serializable {
    private Board board;
    private SackOfTiles sackOfTiles;
    private final PublicObjective[] publicObjectives;
    private final DeckOfPublicObjectives pubObjDeck;
    private final DeckOfPrivateObjectives prvObjDeck;
    private final ArrayList<Player> players;
    private Player firstPlayer;
    private final Stack<Integer>[] pointsPubObj =  new Stack[2];
    private ArrayList<Tile> tilesInCurrPlayerHand;
    private ArrayList<Coords> tempTiles;
    /**
     * Constructor of the game class
     */
    public Game() {
        this.board = new Board();
        this.sackOfTiles = new SackOfTiles();
        this.publicObjectives = new PublicObjective[2];
        this.players = new ArrayList<Player>();

        //non so cosa che mi dice quanti giocatori ci sono
        this.pointsPubObj[0] = new Stack<Integer>();
        this.pointsPubObj[1] = new Stack<Integer>();

        pubObjDeck = new DeckOfPublicObjectives();
        prvObjDeck = new DeckOfPrivateObjectives();

        this.tilesInCurrPlayerHand = new ArrayList<Tile>();
        this.tempTiles = new ArrayList<Coords>();
    }


    /**
     * Initialize method, for filling the board, choosing the common objectives and assigning private objectives to players
     * @author Guido Gonnella
     */
    public void init(){
        //fills the board picking tiles from the sack
        setBoard();

        // adding public/common objective
        ArrayList<PublicObjective> temp = pubObjDeck.getPublicObjective();
        for(int i = 0; i < 2; i++) publicObjectives[i] = temp.get(i);

        //get private objectives and give to player
        //Fill a temporary list with the private objectives
        ArrayList<PrivateObjective> chosenObj = prvObjDeck.getPrivateObjectives(players.size());

        for(Player p : players){
            //give the player the first private objective in the list
            //it resembles the First Out logic
            p.setPrivateObjective(chosenObj.remove(0));
        }

        //initialize stacks for the common goal(public objectives)
        switch (players.size()){
            case 2 -> {
                pointsPubObj[0].push(4);
                pointsPubObj[0].push(8);

                pointsPubObj[1].push(4);
                pointsPubObj[1].push(8);
            }
            case 3 -> {
                pointsPubObj[0].push(4);
                pointsPubObj[0].push(6);
                pointsPubObj[0].push(8);

                pointsPubObj[1].push(4);
                pointsPubObj[1].push(6);
                pointsPubObj[1].push(8);
            }
            case 4 -> {
                pointsPubObj[0].push(2);
                pointsPubObj[0].push(4);
                pointsPubObj[0].push(6);
                pointsPubObj[0].push(8);

                pointsPubObj[1].push(2);
                pointsPubObj[1].push(4);
                pointsPubObj[1].push(6);
                pointsPubObj[1].push(8);
            }
        }
    }

    /**
     * It sets the board ready to be used by the specific number of players in this game
     */
    public void setBoard() {
        Space[][] grid = this.board.getGrid();

        if(players.size()>2) {
            grid[0][3].setAvailable();
            grid[2][2].setAvailable();
            grid[2][6].setAvailable();
            grid[3][8].setAvailable();
            grid[5][0].setAvailable();
            grid[6][2].setAvailable();
            grid[6][6].setAvailable();
            grid[8][5].setAvailable();

            if(players.size()>3) {
                grid[0][4].setAvailable();
                grid[1][5].setAvailable();
                grid[3][1].setAvailable();
                grid[4][0].setAvailable();
                grid[4][8].setAvailable();
                grid[5][7].setAvailable();
                grid[7][3].setAvailable();
                grid[8][4].setAvailable();
            }
        }

        for(int row=1; row<8; row++) {
            switch (row) {
                case 1 -> {
                    grid[row][3].setAvailable();
                    grid[row][4].setAvailable();
                }
                case 2, 6 -> {
                    grid[row][3].setAvailable();
                    grid[row][4].setAvailable();
                    grid[row][5].setAvailable();
                }
                case 3 -> {
                    for (int col = 2; col < 8; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 4 -> {
                    for (int col = 1; col < 8; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 5 -> {
                    for (int col = 1; col < 7; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 7 -> {
                    grid[row][4].setAvailable();
                    grid[row][5].setAvailable();
                }
                default -> {
                }
            }
        }

        this.board.setGrid(grid);
        this.board.fill(this.sackOfTiles);
    }

    public int getNumPlayers() {
        return this.players.size();
    }

    /**
     * return the player object by the nickname
     * @param nick the nickname of the chosen player
     * @return the player object
     * @author Guido Gonnella
     */
    public Player getPlayerByNick(String nick){
        for(Player p : players){
            if(p.getNickname().equals(nick)) return p;
        }
        return null;
    }

    public ArrayList<Tile> getTilesInCurrPlayerHand(){
        return this.tilesInCurrPlayerHand;
    }


    /**
     * The method returns a boolean value, based on the coordinates of the selected tiles from the game Board.<br>
     * It checks firstly if the number of the selected tiles is not greater than three, than based on the number (one, two or three).<br><br>
     * If the tiles are only one, it checks if the tile has at least one free side. <br>
     * If the tiles are two, it checks if the x coordinates or the y coordinates are the same, then it sorts the array containing the coordinates
     * (sorting on the y if the selection is horizontal, or on the x if the selection is vertical), then checks if the absolute value of the difference of the y values
     * (x values) is 1, meaning the tiles are one next to the other, then checks if the selected tiles have one free side.<br>
     * For the selection of three elements, it first checks if the x values or the y values are the same (same row or column), then order the arraylist by the x or the y values,
     * then checks if the difference between the coordinates (x or y values) are -1, meaning the values (x or y) are consecutive, then it checks if the coordinates
     * have at least on free side.
     * @return return true if the selection is valid, false otherwise
     * @author Guido Gonnella
     */
    public boolean validSelection(){
        if(tempTileSelSize() > 0 && tempTileSelSize() <= 3){
            if(tempTileSelSize() == 1){
                return adjacent(tempTiles.get(0).ROW, tempTiles.get(0).COL);
            } else if (tempTileSelSize() == 2) {
                if(tempTiles.get(0).ROW == tempTiles.get(1).ROW){
                    //swap for sorting the two element arrays
                    if(tempTiles.get(0).COL > tempTiles.get(1).COL){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(Math.abs(tempTiles.get(0).COL - tempTiles.get(1).COL) == 1){
                        return adjacent(tempTiles.get(0).ROW, tempTiles.get(0).COL) && adjacent(tempTiles.get(1).ROW, tempTiles.get(1).COL);
                    }
                } else if (tempTiles.get(0).COL == tempTiles.get(1).COL) {
                    //swap for sorting the two element arrays
                    if(tempTiles.get(0).COL > tempTiles.get(1).COL){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(Math.abs(tempTiles.get(0).ROW - tempTiles.get(1).ROW) == 1){
                        return adjacent(tempTiles.get(0).ROW, tempTiles.get(0).COL) && adjacent(tempTiles.get(1).ROW, tempTiles.get(1).COL);
                    }
                }
            } else if (tempTileSelSize() == 3) {
                if(tempTiles.get(0).ROW == tempTiles.get(1).ROW && tempTiles.get(1).ROW == tempTiles.get(2).ROW){
                    //sorting 3 element array
                    if(tempTiles.get(0).COL > tempTiles.get(1).COL){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }
                    if(tempTiles.get(1).COL > tempTiles.get(2).COL){
                        Coords t = tempTiles.get(2);
                        tempTiles.set(2, tempTiles.get(1));
                        tempTiles.set(1, t);
                    }
                    if(tempTiles.get(0).COL > tempTiles.get(1).COL){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(tempTiles.get(0).COL - tempTiles.get(1).COL == -1 && tempTiles.get(1).COL - tempTiles.get(2).COL == -1){
                        return adjacent(tempTiles.get(0).ROW, tempTiles.get(0).COL) && adjacent(tempTiles.get(1).ROW, tempTiles.get(1).COL) && adjacent(tempTiles.get(2).ROW, tempTiles.get(2).COL);
                    }

                }else if(tempTiles.get(0).COL == tempTiles.get(1).COL && tempTiles.get(1).COL == tempTiles.get(2).COL){
                    //sorting 3 element array
                    if(tempTiles.get(0).ROW > tempTiles.get(1).ROW){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }
                    if(tempTiles.get(1).ROW > tempTiles.get(2).ROW){
                        Coords t = tempTiles.get(2);
                        tempTiles.set(2, tempTiles.get(1));
                        tempTiles.set(1, t);
                    }
                    if(tempTiles.get(0).ROW > tempTiles.get(1).ROW){
                        Coords t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(tempTiles.get(0).ROW - tempTiles.get(1).ROW == -1 && tempTiles.get(1).ROW - tempTiles.get(2).ROW == -1){
                        return adjacent(tempTiles.get(0).ROW, tempTiles.get(0).COL) && adjacent(tempTiles.get(1).ROW, tempTiles.get(1).COL) && adjacent(tempTiles.get(2).ROW, tempTiles.get(2).COL);
                    }

                }
            }
        }

        return false;
    }

    /**
     * add to the arraylist, if its size is lower than 3, the coordinates of the tile the current player wants to take from the board
     * @param x x coordinate
     * @param y y coordinate
     */
    public void selectTiles(int x, int y){
        if(tempTiles.size() <= 3 && !tempTiles.contains(new Coords(x, y)) && board.getGrid()[x][y].isAvailable()) {
            this.tempTiles.add(new Coords(x, y));
        }
    }

    /**
     * @return the size of the arraylist containing the temporary selected tiles
     * @author Guido Gonnella
     */
    public int tempTileSelSize(){ return this.tempTiles.size();}

    /**
     * It fills the arraylist TilesInCurrPlayerHand with the tiles at the coordinates stored in the tempTiles arraylist
     * @author Guido Gonnella
     */
    public void fillTilesInHand(){
        for(Coords c: tempTiles){
            tilesInCurrPlayerHand.add(this.board.takeTiles(c.ROW, c.COL).orElse(null));
        }
    }

    /**
     * Class used when the current player takes the tiles from the board
     *
     * @implNote only one tile at a time can be taken
     */
    public Tile takeTiles(int x, int y) {
        return this.board.takeTiles(x,y).orElse(null);
    }

    /**
     * @param x row
     * @param y column
     * @return true if there is at least one free side, false otherwise
     * @author Pierantonio Mauro
     */
    private boolean adjacent(int x, int y){
        if(x==0 || x== 8 || y==0 || y==8) return true;
        if(board.getGrid()[x-1][y].getTile().isEmpty()) return true;
        if(board.getGrid()[x+1][y].getTile().isEmpty()) return true;
        if(board.getGrid()[x][y-1].getTile().isEmpty()) return true;
        return board.getGrid()[x][y + 1].getTile().isEmpty();
    }

    /**
     * It sets the two random public objectives
     *
     * @param deckOfPublicObjectives deck that contains all the possible public objectives
     */
    public void setPublicObjectives(DeckOfPublicObjectives deckOfPublicObjectives) {
        ArrayList<PublicObjective> temp = deckOfPublicObjectives.getPublicObjective();
        this.publicObjectives[0] = temp.get(0);
        this.publicObjectives[1] = temp.get(1);
    }

    /**
     * Getter class of the attribute publicObjectives of the object Game
     *
     * @return the publicObjectives of the game
     */
    public PublicObjective[] getPublicObjectives() {
        return this.publicObjectives;
    }
    public PubObjType[] getPublicObjectivesType(){
        PubObjType[] pubObjTypes;
        pubObjTypes = new PubObjType[2];
        pubObjTypes[0] = publicObjectives[0].getObjectiveType();
        pubObjTypes[1] = publicObjectives[1].getObjectiveType();
        return(pubObjTypes);
    }

    /**
     * Getter class of the attribute board of the object Game
     *
     * @return the board of the game
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Getter class of the attribute sackOfTiles of the object Game
     *
     * @return the sackOfTiles of the game
     */
    public SackOfTiles getSackOfTiles() {
        return this.sackOfTiles;
    }

    /**
     * It updates the current saved sack of remaining tiles
     *
     * @param sackOfTiles sack that contains all the remaining tiles
     */
    public void setSackOfTiles(SackOfTiles sackOfTiles) {
        this.sackOfTiles = sackOfTiles;
    }

    /**
     * Overload of the previous method, instead of using the current player attribute,
     * which is stored in the {@link it.polimi.ingsw.Controller.GameController gameController class}
     * it use the player passed as a parameter.
     * @param player the player to check
     */
    public void reachPubObj(Player player) {
        for(int i=0; i<2; i++) {
            if(!player.getPubObjFlag()[i]) {
                if(publicObjectives[i].getResultObjective(player.getShelf())) {
                    player.updatePubObjFlag(i);
                    player.addPoints(pointsPubObj[i].pop());
                }
            }
        }
    }

    /**
     * Method that adds a player to the game
     *
     * @param name player's nickname
     * @author Pierantonio Mauro
     */
    public void addPlayer(String name){
        this.players.add(new Player(name));
    }

    /**
     * Return the list of nicknames of all players
     * @return an ArrayList of strings containing the nicknames
     * @author Guido Gonnella
     */
    public ArrayList<String> getPlayerList(){
        ArrayList<String> nicks = new ArrayList<String>();
        for(Player p : players) nicks.add(p.getNickname());

        return nicks;
    }

    public void setFirstPlayer(String nickname){
        this.firstPlayer = getPlayerByNick(nickname);
    }

    /**
     * Getter for the firstPlayer
     * @return the nickname of the firstPlayer
     */
    public String getFirstPlayer(){
        return firstPlayer.getNickname();
    }


}
