package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Enumeration.GameState;
import it.polimi.ingsw.Enumeration.TurnState;
import it.polimi.ingsw.Network.Message.C2S.FullTileSelectionMessage;
import it.polimi.ingsw.Network.Message.C2S.TextMessage;
import it.polimi.ingsw.Network.Message.EndGameMessage;
import it.polimi.ingsw.Enumeration.MsgType;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.S2C.AskFullMsg;
import it.polimi.ingsw.Network.Message.S2C.PublicObjectiveMessage;
import it.polimi.ingsw.Network.Message.S2C.UpdateBoardMessage;
import it.polimi.ingsw.Network.ServerPack.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;
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

    //VirtualView
    /**
     * used for sending and receiving Messages from the client
     */
    private final VirtualView virtualView;

    //flags & utils
    private boolean shelfFull;

    public GameController(VirtualView vv) {
        //creating the game instance
        game = new Game();
        selectedTiles = new ArrayList<>();
        players = new ArrayList<>();
        gameState = GameState.INIT;
        turnState = TurnState.SELECT_PHASE;
        virtualView = vv;
        shelfFull = false;

        for(String username : virtualView.getUsernames()) {
            game.addPlayer(username);
            players.add(username);
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            switch (gameState){
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
        game.init();    // initializing the game

        currPlayer = players.get(0); // setting the first player

        //starting the game phase
        gameState = GameState.IN_GAME;
        turnState = TurnState.SELECT_PHASE;
    }

    /**
     * Method to add the points for the players and/or calls the inGame() function if not all the players have finished the last turn.<br>
     * It broadcast to the players the points and other stats
     */
    private void end(){
        virtualView.writeBroadcast(new TextMessage("Game's ended!"));
        virtualView.writeBroadcast(new UpdateBoardMessage(game.getBoard()));
        Map<String, Integer> playerPoints = new HashMap<>();

        int max = 0;
        String winner = null;

        for(String player : players){
            Player p = game.getPlayerByNick(player);
            virtualView.write(player, MsgType.SHELF_UPDATE, p.getShelf());

            //adds the points from private objectives and adjacent tiles
            p.countPoints();
            playerPoints.put(player, p.getPlayerPoints());
            virtualView.writeBroadcast(new TextMessage( player + " scored " +  p.getPlayerPoints()));

            if(p.getPlayerPoints() > max){
                max = p.getPlayerPoints();
                winner = player;
            }
        }

        virtualView.writeBroadcast(new TextMessage(winner + " is the winner!"));
        virtualView.writeBroadcast(new EndGameMessage());
        for(String p : players)
            virtualView.removeUsername(p);

        Thread.currentThread().interrupt();
    }

    /**
     * Handle the game turns and flow
     */
    private void inGame(){
        switch (turnState){
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
        virtualView.writeBroadcast(new TextMessage("It's "+ currPlayer + " turn\n"));
        virtualView.write(currPlayer, MsgType.PUBLIC_OBJECTIVE, game.getPublicObjectives());
        virtualView.write(currPlayer, MsgType.PRIVATE_OBJECTIVE, game.getPlayerByNick(currPlayer).getPrivateObjective());

        //broadcast the updated board to all players
        virtualView.writeBroadcast(new UpdateBoardMessage(game.getBoard()));

        selectedTiles.clear();
        FullTileSelectionMessage response = virtualView.readAll(currPlayer, new AskFullMsg(game.getBoard(), game.getPlayerByNick(currPlayer).getShelf()));
        ArrayList<Coords> tilesCoords = response.getCoords();
        int column = response.getColumn();
        Tile tile;

        for (Coords tilesCoord : tilesCoords) {
            tile = game.getBoard().takeTiles(tilesCoord.x, tilesCoord.y).get();
            game.getPlayerByNick(currPlayer).getShelf().putTile(tile, column);
        }

        //checking if the current player's shelf is full
        if(game.getPlayerByNick(currPlayer).getShelf().isFull() && !shelfFull) {
            shelfFull = true;
            game.getPlayerByNick(currPlayer).addPoints(1);
        }

        //checking if the current player has reached a common goal/public objective
        //it can add points to the player if they have passed an objective
        game.reachPubObj(game.getPlayerByNick(currPlayer));

        //sends the shelf to the current player
        virtualView.write(currPlayer, MsgType.SHELF_UPDATE, game.getPlayerByNick(currPlayer).getShelf());

        //change the turn phase and the pick the next player
        turnState = TurnState.END;
    }

    /**
     * Handle the end of the turn
     */
    private void endTurn(){
        if(shelfFull && players.indexOf(currPlayer) == players.size()-1)
            gameState = GameState.END;
        else {
            //go to the next player
            nextPlayer();
            turnState = TurnState.SELECT_PHASE;
        }
    }

    /**
     * Select the next player in the player list<br>
     * It select the next index and put under the modulus of the number of players
     */
    private void nextPlayer(){
        int currPlayerIndex = players.indexOf(currPlayer);
        int nextPlayerIndex = (currPlayerIndex+1) % players.size();

        currPlayer = players.get(nextPlayerIndex);
    }

}