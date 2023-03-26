package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;
import java.util.Random;

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
    private final Stack<Integer>[] pointsPubObj;

    /**
     * Constructor of the game class
     */
    public Game() {
        this.board = new Board();
        this.sackOfTiles = new SackOfTiles();
        this.publicObjectives = new PublicObjective[2];
        this.players = new ArrayList<Player>();
        //non so cosa che mi dice quanti giocatori ci sono
        this.pointsPubObj = new Stack[this.players.size()];

        Random rand = new Random();
        int index = rand.nextInt(this.players.size());
        this.firstPlayer = new Player(this.players.get(index).getNickname());
        this.currentPlayer = new Player(this.players.get(index).getNickname());
    }

    /**
     * Class used when the current player take the tiles from the board
     *
     * @implNote only one tile at time can be taken
     */
    public void takeTiles() {
        int x, y, col;
        Optional<Tile>[] temp;
        //coordinate e colonna prese da input
        for(int i=0; i<2; i++){
            //verifica che si possano prendere altre tiles
            if(){
                temp[i] = this.board.takeTiles(x, y);
            }
        }
        //qualcosa in mezzo per decidere l'ordine o simile
        currentPlayer.placeTile(temp[], col);
    }

    /**
     * It sets the two random public objectives
     *
     * @param deckOfPublicObjectives deck that contains all the possible public objectives
     */
    public void setPublicObjectives(DeckOfPublicObjectives deckOfPublicObjectives) {
        this.publicObjectives[0] = deckOfPublicObjectives.getPublicObjective().get(0);
        this.publicObjectives[1] = deckOfPublicObjectives.getPublicObjective().get(1);
    }

    /**
     * It takes the random private objectives for every player from the deck, and it gives them to the players
     *
     * @param deckOfPrivateObjectives deck that contains all the possible private objectives
     */
    public void setPrivateObjectives(DeckOfPrivateObjectives deckOfPrivateObjectives) {
        for(int i=0; i<this.players.size(); i++) {
            this.privateObjectives[i] = deckOfPrivateObjectives.getPrivateObjective().get(i);
            players.get(i) = deckOfPrivateObjectives.getPrivateObjectives().get(i);
        }
    }

    /**
     * It sets the board ready to be used by the specific number of players in this game
     *
     * @param board empty and uninitialized board
     */
    public void setBoard(Board board) {
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
}
