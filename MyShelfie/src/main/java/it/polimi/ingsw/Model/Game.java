package it.polimi.ingsw.Model;

import exceptions.SackEmptyException;

import java.util.*;

/**
 * Class that describes the game, all of its most important methods and attributes
 * It contains all the data and information necessary to permit the game to be saved and resumed if interrupted
 * This class is also used to initialize all the components at the very beginning of every game and keeping them updated
 *
 * @author Samuele Mazzoleni
 */

public class Game {
    private Board board;
    private SackOfTiles sackOfTiles;
    private final PublicObjective[] publicObjectives;
    private final ArrayList<Player> players;
    private final Player firstPlayer;
    private Player currentPlayer;
    private final Stack<Integer>[] pointsPubObj =  new Stack[2];
    private ArrayList<Tile> tilesInCurrPlayerHand;
    private ArrayList<coord> tempTiles;
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

        Random rand = new Random();
        int index = rand.nextInt(this.players.size());
        this.firstPlayer = new Player(this.players.get(index).getNickname());
        this.currentPlayer = new Player(this.players.get(index).getNickname());
        this.tilesInCurrPlayerHand = new ArrayList<Tile>();
        this.tempTiles = new ArrayList<coord>();
    }

    /**
     * when in the controller is sensed a new player its added to the game
     *
     * @author Pierantonio Mauro
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    public Player getPlayerByNick(String nick){
        for(Player p : players){
            if(p.getNickname().equals(nick)) return p;
        }
        return null;
    }

    public ArrayList<Tile> getTilesInCurrPlayerHand(){
        return (ArrayList<Tile>) this.tilesInCurrPlayerHand.clone();
    }
    /**
     *
     * @return return true if the tiles selected is valid, in line and with at least a side free
     */
    public boolean validSelection(){
        if(tempTileSelSize() > 0 && tempTileSelSize() <= 3){
            if(tempTileSelSize() == 1){
                return adjacent(tempTiles.get(0).x, tempTiles.get(0).y);
            } else if (tempTileSelSize() == 2) {
                if(tempTiles.get(0).x == tempTiles.get(1).x){
                    //swap for sorting the two element array
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        coord t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(Math.abs(tempTiles.get(0).y - tempTiles.get(1).y) == 1){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y);
                    }
                } else if (tempTiles.get(0).y == tempTiles.get(1).y) {
                    //swap for sorting the two element array
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        coord t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if(Math.abs(tempTiles.get(0).x - tempTiles.get(1).x) == 1){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y);
                    }
                }
            } else if (tempTileSelSize() == 3) {
                if(tempTiles.get(0).x == tempTiles.get(1).x && tempTiles.get(1).x == tempTiles.get(2).x){
                    //sorting 3 element array
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        coord t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }
                    if(tempTiles.get(1).y > tempTiles.get(2).y){
                        coord t = tempTiles.get(2);
                        tempTiles.set(2, tempTiles.get(1));
                        tempTiles.set(1, t);
                    }
                    if(tempTiles.get(0).y > tempTiles.get(1).y){
                        coord t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if((tempTiles.get(0).y - tempTiles.get(1).y == -1 && tempTiles.get(1).y - tempTiles.get(2).y == -1 )||
                        (tempTiles.get(0).y - tempTiles.get(1).y == 1 && tempTiles.get(1).y - tempTiles.get(2).y == 1 )){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y) && adjacent(tempTiles.get(2).x, tempTiles.get(2).y);
                    }

                }else if(tempTiles.get(0).y == tempTiles.get(1).y && tempTiles.get(1).y == tempTiles.get(2).y){
                    //sorting 3 element array
                    if(tempTiles.get(0).x > tempTiles.get(1).x){
                        coord t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }
                    if(tempTiles.get(1).x > tempTiles.get(2).x){
                        coord t = tempTiles.get(2);
                        tempTiles.set(2, tempTiles.get(1));
                        tempTiles.set(1, t);
                    }
                    if(tempTiles.get(0).x > tempTiles.get(1).x){
                        coord t = tempTiles.get(1);
                        tempTiles.set(1, tempTiles.get(0));
                        tempTiles.set(0, t);
                    }

                    if((tempTiles.get(0).x - tempTiles.get(1).x == -1 && tempTiles.get(1).x - tempTiles.get(2).x == -1 )||
                        (tempTiles.get(0).x - tempTiles.get(1).x == 1 && tempTiles.get(1).x - tempTiles.get(2).x == 1 )){
                        return adjacent(tempTiles.get(0).x, tempTiles.get(0).y) && adjacent(tempTiles.get(1).x, tempTiles.get(1).y) && adjacent(tempTiles.get(2).x, tempTiles.get(2).y);
                    }

                }
            }
        }

        return false;
    }

    /**
     * add to the arraylist, if its size is lower than 3, the coordinates of the tile the current player want to take from the board
     * @param x x coordinate
     * @param y y coordinate
     */
    public void selectTiles(int x, int y){
        if(tempTiles.size() <= 3 && !tempTiles.contains(new coord(x, y)) && board.getGrid()[x][y].isAvailable()) {
            this.tempTiles.add(new coord(x, y));
        }
    }

    /**
     * @return  the size of the arraylist containg the temporary selected tiles
     * @author Guido Gonnella
     */
    public int tempTileSelSize(){ return this.tempTiles.size();}

    /**
     * It fills the arraylist TilesInCurrPlayerHand with the tiles at the coordinates stored in the tempTiles arraylist
     * @author Guido Gonnella
     */
    public void fillTilesInHand(){
        for(coord c: tempTiles){
            tilesInCurrPlayerHand.add(this.board.takeTiles(c.x, c.y).orElse(null));
        }
    }

    /**
     * Class used when the current player take the tiles from the board
     *
     * @implNote only one tile at time can be taken
     */
    public Tile takeTiles(int x, int y) {
        /*Scanner scanner = new Scanner(System.in);
        int x, y, col;
        String stop;
        ArrayList<Optional<Tile>> temp = new ArrayList<>();

        do{
            System.out.println("Vuoi continuare a pescare? s/n");
            stop = scanner.next();
            x = scanner.nextInt();
            y = scanner.nextInt();
            if(!adjacent(x,y) && x>=0){
                temp.add(this.board.takeTiles(x, y));
            }
        }while(Objects.equals(stop, "s") || Objects.equals(stop, "S"));

        //qualcosa in mezzo per decidere l'ordine o simile
        System.out.println("Seleziona la colonna della libreria:");
        col = scanner.nextInt();
        do {
            System.out.println("Seleziona la tessera da inserire:");
            for(int i=0; i<temp.size(); i++){
                System.out.println(i+1);
                System.out.println(" : ");
                System.out.println(temp.get(i));
                System.out.println("\n");
            }
            int tempTile = scanner.nextInt();
            currentPlayer.placeTile(temp.get(tempTile).orElse(null),col);
            il .orElse(null) serve per passare come parametro una tile e non
            un operational di tile
            temp.remove(tempTile);
        }while(!temp.isEmpty());
        */

        // il .orElse(null) serve per trasformare un Optional<Tile> in Tile
        return  this.board.takeTiles(x,y).orElse(null);
    }

    /**
     * @param x row
     * @param y column
     * @return true if there is at least one free side, false otherwise
     * @author Pierantonio Mauro
     */
    private boolean adjacent(int x, int y){
        if(x==0 || x== 8 || y==0 || y==8)
            return true;
        if(board.getGrid()[x-1][y].getTile().isEmpty())
            return true;
        if(board.getGrid()[x+1][y].getTile().isEmpty())
            return true;
        if(board.getGrid()[x][y-1].getTile().isEmpty())
            return true;
        if(board.getGrid()[x][y+1].getTile().isEmpty())
            return true;
        return false;
    }

    /**
     * It sets the two random public objectives
     *
     * @param deckOfPublicObjectives deck that contains all the possible public objectives
     */
    public void setPublicObjectives(DeckOfPublicObjectives deckOfPublicObjectives) {
        ArrayList<PublicObjective> temp = new ArrayList<PublicObjective>(deckOfPublicObjectives.getPublicObjective());
        this.publicObjectives[0] = temp.get(0);
        this.publicObjectives[1] = temp.get(1);
    }

    /**
     * It takes the random private objectives for every player from the deck, and it gives them to the players
     *
     * @param deckOfPrivateObjectives deck that contains all the possible private objectives
     */
    public void setPrivateObjectives(DeckOfPrivateObjectives deckOfPrivateObjectives) {

        ArrayList <PrivateObjective> temp;
        temp=deckOfPrivateObjectives.getPrivateObjectives(this.players.size());
        for(int i=0; i<this.players.size(); i++) {
            players.get(i).setPrivateObjective(temp.get(i));
        }
    }

    /**
     * It sets the board ready to be used by the specific number of players in this game
     *
     * @param board empty and uninitialized board
     */
    public void setBoard(Board board) throws SackEmptyException {
        Space[][] grid = this.board.getGrid();

        if(this.players.size()>2) {
            grid[0][3].setAvailable();
            grid[2][2].setAvailable();
            grid[2][6].setAvailable();
            grid[3][8].setAvailable();
            grid[5][0].setAvailable();
            grid[6][2].setAvailable();
            grid[6][6].setAvailable();
            grid[8][5].setAvailable();

            if(this.players.size()>3) {
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
                case 1: {
                    grid[row][3].setAvailable();
                    grid[row][4].setAvailable();
                }
                case 2: {
                    grid[row][3].setAvailable();
                    grid[row][4].setAvailable();
                    grid[row][5].setAvailable();
                }
                case 3: {
                    for (int col = 2; col < 8; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 4: {
                    for (int col = 1; col < 8; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 5: {
                    for (int col = 1; col < 7; col++) {
                        grid[row][col].setAvailable();
                    }
                }
                case 6: {
                    grid[row][3].setAvailable();
                    grid[row][4].setAvailable();
                    grid[row][5].setAvailable();
                }
                case 7: {
                    grid[row][4].setAvailable();
                    grid[row][5].setAvailable();
                }
            }
        }

        this.board.setGrid(grid);
        this.board.fill(this.sackOfTiles);
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
     * It decides if the current player have completed the public objectives remained to him
     * and gives him the points in case he completed it
     *
     * @author Samuele Mazzoleni
     * @author Pierantonio Mauro
     */
    public void reachPubObj() {
        for(int i=0; i<2; i++) {
            if(!this.currentPlayer.getPubObjFlag()[i]) {
                if(this.publicObjectives[i].getResultObjective(this.currentPlayer.getShelf())){
                    //setPubObjFlag will be replaced by updatePubObjFlag
                    this.currentPlayer.setPubObjFlag(i);
                    this.currentPlayer.addPoints(pointsPubObj[i].pop());
                }
            }
        }
    }

    /**
     * Method that adds a player to the game
     *
     * @param name playerp's nickname
     * @author Pierantonio Mauro
     */
    public void addPlayer(String name){
        this.players.add(new Player(name));
    }
}
