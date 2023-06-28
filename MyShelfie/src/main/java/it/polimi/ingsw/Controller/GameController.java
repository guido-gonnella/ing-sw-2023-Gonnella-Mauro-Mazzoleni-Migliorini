package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Enumeration.GameState;
import it.polimi.ingsw.Enumeration.TurnState;
import it.polimi.ingsw.Network.Message.C2S.FullTileSelectionMessage;
import it.polimi.ingsw.Enumeration.MsgType;
import it.polimi.ingsw.Network.Message.S2C.AskFullMsg;
import it.polimi.ingsw.Network.ServerPack.VirtualView;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for the game<br>
 * It is used server-side.
 * @author Guido Gonnella
 */
public class GameController implements Runnable{
    //Game and turn states
    private GameState gameState;
    private TurnState turnState;

    //Players
    /**
     * List of the players, used also as the player order in the game
     */
    private final ArrayList<String> players;

    /**
     * Current player
     */
    private String currPlayer;

    private final Game game;
    private ArrayList<Tile> selectedTiles;

    /**
     * used for sending and receiving Messages from the client
     */
    private final VirtualView virtualView;

    /**
     * To flag if someone in the game has their shelf full, which means for the remaining players in the order is the last turn.
     */
    private boolean shelfFull;

    /**
     * Constructor for the GameController
     * @param vv virtual view used to communicated with the clients
     */
    public GameController(VirtualView vv) {
        //creating the game instance
        this.game = new Game();
        this.selectedTiles = new ArrayList<>();
        this.players = new ArrayList<>();
        this.gameState = GameState.INIT;
        this.turnState = TurnState.SELECT_PHASE;
        this.virtualView = vv;
        this.shelfFull = false;

        for(String username : virtualView.getUsernames()) {
            this.game.addPlayer(username);
            this.players.add(username);
        }
    }

    /**
     * Run method from the Runnable interface implemented to cycle and select which function to call based on the gameState
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            switch (this.gameState) {
                case INIT -> init();
                case IN_GAME -> inGame();
                case END -> end();
            }
        }
    }

    /**
     * Initialization of the game
     */
    private void init(){
        this.game.init();    // initializing the game

        this.currPlayer = this.players.get(0); // setting the first player

        //starting the game phase
        this.gameState = GameState.IN_GAME;
        this.turnState = TurnState.SELECT_PHASE;
    }

    /**
     * Method to add the points for the players and/or calls the inGame() function if not all the players have finished the last turn.<br>
     * It broadcast to the players the points and other stats
     */
    private void end() {
        this.virtualView.writeBroadcast(MsgType.TEXT, "\n================= GAME'S ENDED! =================\n\n");
        this.virtualView.writeBroadcast(MsgType.BOARD_UPDATE, game.getBoard());
        this.virtualView.writeBroadcast(MsgType.TEXT, "\n");
        Map<String, Integer> mapPoints = new HashMap<>();
        Map<String, boolean[]> mapObjective = new HashMap<>();

        int max = 0;
        String winner = null;

        for(String player : this.players){
            Player p = this.game.getPlayerByNick(player);
            this.virtualView.write(player, MsgType.SHELF_UPDATE, p.getShelf());

            //adds the points from private objectives and adjacent tiles
            p.countPoints();
            mapPoints.put(player, p.getPlayerPoints());
            mapObjective.put(player, p.getPubObjFlag());

            if(p.getPlayerPoints() > max){
                max = p.getPlayerPoints();
                winner = player;
            }
        }

        this.virtualView.endGame(mapPoints, mapObjective);
        this.virtualView.writeBroadcast(MsgType.TEXT, "\n================= " + "\u001B[35m" + winner + "\u001B[0m" + " WINNER WINNER CHICKEN DINNER! ＼(＾O＾)／ =================");
        this.virtualView.writeBroadcast(MsgType.END_GAME, null);
        for(String p : this.players) this.virtualView.removeUsername(p);
        Thread.currentThread().interrupt();
        //this.virtualView.disconnectAll();
    }

    /**
     * Handle the game turns and flow
     */
    private void inGame(){
        switch (this.turnState){
            case SELECT_PHASE -> select();
            case END -> endTurn();
        }
    }

    /**
     * Handle the phase of selecting the tiles from the board and the column of the shelf
     * where to put the selected tiles
     */
    private void select(){
        //broadcast the player that is playing
        this.virtualView.writeBroadcast(MsgType.TEXT, "\n================= IT'S " + "\u001B[35m" + this.currPlayer + "\u001B[0m" + "'S TURN =================\n");

        this.virtualView.write(this.currPlayer, MsgType.PUBLIC_OBJECTIVE, this.game.getPublicObjectivesType());
        this.virtualView.write(this.currPlayer, MsgType.PRIVATE_OBJECTIVE, this.game.getPlayerByNick(this.currPlayer).getPrivateObjective());
        this.virtualView.write(this.currPlayer, MsgType.SHELF_UPDATE, this.game.getPlayerByNick(this.currPlayer).getShelf());
        //broadcast the updated board to all players
        virtualView.writeBroadcast(MsgType.BOARD_UPDATE, game.getBoard());

        this.selectedTiles.clear();
        FullTileSelectionMessage response = this.virtualView.readAll(this.currPlayer, new AskFullMsg(game.getBoard(), this.game.getPlayerByNick(this.currPlayer).getShelf()));
        ArrayList<Coords> tilesCoords = response.getCoords();
        int column = response.getColumn();

        for (Coords CoordsOfTile : tilesCoords) {
            if (this.game.getBoard().getGrid()[CoordsOfTile.ROW][CoordsOfTile.COL].getTile().isPresent()) {
                Tile tile = this.game.getBoard().takeTiles(CoordsOfTile.ROW, CoordsOfTile.COL).get();
                this.game.getPlayerByNick(this.currPlayer).getShelf().putTile(tile, column);
            }
        }

        //checking if the current player's shelf is full
        if(!this.shelfFull && this.game.getPlayerByNick(this.currPlayer).getShelf().isFull()) {
            this.shelfFull = true;
            this.game.getPlayerByNick(this.currPlayer).addPoints(1);
        }

        //checking if the current player has reached a common goal/public objective
        //it can add points to the player if they have passed an objective
        this.game.reachPubObj(this.game.getPlayerByNick(this.currPlayer));

        //sends the shelf to the current player
        this.virtualView.write(this.currPlayer, MsgType.SHELF_UPDATE, this.game.getPlayerByNick(this.currPlayer).getShelf());

        //change the turn phase and the pick the next player
        this.turnState = TurnState.END;
    }

    /**
     * Handle the end of the turn
     */
    private void endTurn(){
        if(this.shelfFull && this.players.indexOf(this.currPlayer) == this.players.size()-1) {
            this.gameState = GameState.END;
            this.virtualView.writeBroadcast(MsgType.TEXT, "\n================= " + "\u001B[35m" + this.currPlayer + "\u001B[0m" + " FILLED THEIR SHELF! =================\n");
        }
        else {
            if(!this.game.getBoard().checkFill()) {
                this.game.getBoard().fill(this.game.getSackOfTiles());
            }

            //go to the next player
            nextPlayer();
            this.turnState = TurnState.SELECT_PHASE;
        }
    }

    /**
     * Select the next player in the player list<br>
     * It select the next index and put under the modulus of the number of players
     */
    private void nextPlayer(){
        int currPlayerIndex = this.players.indexOf(this.currPlayer);
        int nextPlayerIndex = (currPlayerIndex+1) % this.players.size();

        this.currPlayer = this.players.get(nextPlayerIndex);
    }
}