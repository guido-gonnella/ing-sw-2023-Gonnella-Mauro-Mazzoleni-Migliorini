package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Enumeration.GameState;
import it.polimi.ingsw.Enumeration.TurnState;
import it.polimi.ingsw.Network.Message.C2S.TextMessage;
import it.polimi.ingsw.Network.Message.EndGameMessage;
import it.polimi.ingsw.Network.Message.MsgType;
import it.polimi.ingsw.Network.Message.S2C.UpdateBoardMessage;
import it.polimi.ingsw.Network.ServerPack.VirtualView;

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

        players = new ArrayList<String>();

        gameState = GameState.INIT;

        virtualView = vv;

        shelfFull = false;

        for(String username: virtualView.getUsernames()) {
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

    private void init(){
        for(String player : players){
            game.addPlayer(player);
        }
        game.init();    // initializing the game

        currPlayer = players.get(0); // setting the first player

        //starting the game phase
        gameState = GameState.IN_GAME;
        turnState = TurnState.PICK_TILES;
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

            if(p.getPlayerPoints()>max){
                max = p.getPlayerPoints();
                winner = player;
            }
        }

        virtualView.writeBroadcast(new TextMessage(winner + " is the winner!"));
        virtualView.writeBroadcast(new EndGameMessage());
        for(String p : players){
            virtualView.removeUsername(p);
        }

        Thread.currentThread().interrupt();
    }

    /**
     * Handle the game turns and flow
     */
    private void inGame(){
        switch (turnState){
            case PICK_TILES -> pickTiles();
            case SELECT_COLUMN -> selectColumn();
            case END -> endTurn();
        }
    }

    private void select(){

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
            turnState = TurnState.PICK_TILES;
        }
    }

    /**
     * Handle the phase of placing the tiles in the shelf
     */
    private void selectColumn(){
        virtualView.write(currPlayer, MsgType.SHELF_UPDATE, game.getPlayerByNick(currPlayer).getShelf());
        int column = (int) virtualView.read(currPlayer, MsgType.SELECT_COL_REQUEST);

        //inserting the tiles in the current player's shelf
        for(Tile t : selectedTiles)
            game.getPlayerByNick(currPlayer).getShelf().putTile(t, column);


        //checking if the current player's shelf is full
        if(game.getPlayerByNick(currPlayer).getShelf().isFull() && !shelfFull) {
            shelfFull = true;
            game.getPlayerByNick(currPlayer).addPoints(1);
        }

        //checking if the current player has reached a common goad/public objective
        //it can add points to the player if they have passed an objective
        game.reachPubObj(game.getPlayerByNick(currPlayer));

        //sends the shelf to the current player
        virtualView.write(currPlayer, MsgType.SHELF_UPDATE, game.getPlayerByNick(currPlayer).getShelf());


        //change the turn phase and the pick the next player
        turnState = TurnState.END;
    }

    /**
     * Handle the phase of picking the tiles from the board
     */
    private void pickTiles(){
        //broadcast the player that is playing
        virtualView.writeBroadcast(new TextMessage("It's "+ currPlayer + " turn\n"));

        //broadcast the updated board to all players
        virtualView.writeBroadcast(new UpdateBoardMessage(game.getBoard()));

        selectedTiles = new ArrayList<Tile>();
        ArrayList<Coords> tilesCoords = (ArrayList<Coords>) virtualView.read(currPlayer, MsgType.SELECT_TILE_REQUEST);

        //updating in the model the board and the player's shelf
        for (Coords tilesCoord : tilesCoords) {
            if (game.getBoard().takeTiles(tilesCoord.x, tilesCoord.y).isPresent())
                selectedTiles.add(game.getBoard().takeTiles(tilesCoord.x, tilesCoord.y).get());
        }


        //change the turn phase
        turnState = TurnState.SELECT_COLUMN;
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