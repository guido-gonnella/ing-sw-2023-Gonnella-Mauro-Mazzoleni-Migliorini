package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Enumeration.GameState;
import it.polimi.ingsw.Enumeration.TurnState;
import it.polimi.ingsw.Network.Message.C2S.FullTileSelectionMessage;
import it.polimi.ingsw.Network.Message.S2C.TextMessage;
import it.polimi.ingsw.Network.Message.EndGameMessage;
import it.polimi.ingsw.Enumeration.MsgType;
import it.polimi.ingsw.Network.Message.S2C.AskFullMsg;
import it.polimi.ingsw.Network.Message.S2C.UpdateBoardMessage;
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

    //flags & utils
    private boolean shelfFull;

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
        this.virtualView.writeBroadcast(MsgType.TEXT, "================= GAME'S ENDED! =================");
        this.virtualView.writeBroadcast(MsgType.BOARD_UPDATE, game.getBoard());
        Map<String, Integer> playerPoints = new HashMap<>();

        int max = 0;
        String winner = null;

        for(String player : this.players){
            Player p = this.game.getPlayerByNick(player);
            this.virtualView.write(player, MsgType.SHELF_UPDATE, p.getShelf());

            //adds the points from private objectives and adjacent tiles
            p.countPoints();
            playerPoints.put(player, p.getPlayerPoints());
            this.virtualView.writeBroadcast(MsgType.TEXT, player + " scored " + p.getPlayerPoints() + " points!");

            if(p.getPlayerPoints() > max){
                max = p.getPlayerPoints();
                winner = player;
            }
        }

        this.virtualView.writeBroadcast(MsgType.TEXT, winner + " WINNER WINNER CHICKEN DINNER! ＼(＾O＾)／");
        this.virtualView.writeBroadcast(MsgType.END_GAME, null);
        for(String p : this.players)
            this.virtualView.removeUsername(p);

        Thread.currentThread().interrupt();
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
        this.virtualView.write(this.currPlayer, MsgType.TEXT, "\n------------- PUBLIC OBJECTIVES -------------\n\n");
        this.virtualView.write(this.currPlayer, MsgType.PUBLIC_OBJECTIVE, this.game.getPublicObjectivesType());
        this.virtualView.write(this.currPlayer, MsgType.TEXT, "------------- PRIVATE OBJECTIVE -------------\n\n");
        this.virtualView.write(this.currPlayer, MsgType.PRIVATE_OBJECTIVE, this.game.getPlayerByNick(this.currPlayer).getPrivateObjective());
        this.virtualView.write(this.currPlayer, MsgType.TEXT, "\n------------------- SHELF -------------------\n\n");
        this.virtualView.write(this.currPlayer, MsgType.SHELF_UPDATE, this.game.getPlayerByNick(this.currPlayer).getShelf());

        //broadcast the updated board to all players
        this.virtualView.writeBroadcast(MsgType.TEXT, "\n--------------- UPDATED BOARD ---------------\n\n");
        virtualView.writeBroadcast(MsgType.BOARD_UPDATE, game.getBoard());
        this.virtualView.write(this.currPlayer, MsgType.TEXT, "\n\n");

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
        this.virtualView.write(this.currPlayer, MsgType.TEXT, "\n--------------- UPDATED SHELF ---------------\n\n");
        this.virtualView.write(this.currPlayer, MsgType.SHELF_UPDATE, this.game.getPlayerByNick(this.currPlayer).getShelf());

        //change the turn phase and the pick the next player
        this.turnState = TurnState.END;
    }

    /**
     * Handle the end of the turn
     */
    private void endTurn(){
        if(this.shelfFull && this.players.indexOf(this.currPlayer) == this.players.size()-1)
            this.gameState = GameState.END;
        else {
            //go to the next player
            nextPlayer();
            this.turnState = TurnState.SELECT_PHASE;
            if(!this.game.getBoard().checkFill()) {
                this.game.getBoard().fill(this.game.getSackOfTiles());
            }
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

    //todo temporanei, da togliere
    public void boardShow(Space[][] board){
        PrintStream out = new PrintStream(System.out);
        out.print("-");
        for (int i=0;i<board.length;i++) {
            out.print("\u001B[30m" + "-" + "\u001B[0m" + i + "\u001B[30m" + "-" + "\u001B[0m");
        }
        out.print("\n");
        for(int i = 0; i < board.length; i++) {
            out.print(i);
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j].getTile().isPresent()) {
                    switch (board[i][j].getTile().get().getType()) {
                        case TROPHY -> out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                }
                else {
                    out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            out.print("\n");
        }
    }

    public void shelfShow(SerializableOptional<Tile>[][] shelf) {
        PrintStream out = new PrintStream(System.out);
        out.print("-");
        for (int i=0;i<5;i++) {
            out.print("\u001B[30m" + "-" + "\u001B[0m" + i + "\u001B[30m" + "-" + "\u001B[0m");
        }
        out.print ("\n");
        for (int i = 0; i < shelf.length; i++) {
            out.print(i);
            for (int j = 0; j < shelf[0].length; j++) {
                if (shelf[i][j].isPresent()) {
                    switch (shelf[i][j].get().getType()) {
                        case TROPHY -> out.print("\u001B[36m" + "[T]" + "\u001B[0m");
                        case FRAME -> out.print("\u001B[34m" + "[F]" + "\u001B[0m");
                        case PLANT -> out.print("\u001B[35m" + "[P]" + "\u001B[0m");
                        case GAME -> out.print("\u001B[33m" + "[G]" + "\u001B[0m");
                        case BOOK -> out.print("\u001B[37m" + "[B]" + "\u001B[0m");
                        case CAT -> out.print("\u001B[32m" + "[C]" + "\u001B[0m");
                        default -> out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                    }
                } else {
                    System.out.print("\u001B[30m" + "[■]" + "\u001B[0m");
                }
            }
            System.out.print("\n");
        }
    }
}