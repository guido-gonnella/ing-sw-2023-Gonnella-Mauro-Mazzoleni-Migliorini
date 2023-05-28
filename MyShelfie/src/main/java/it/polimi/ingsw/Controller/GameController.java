package it.polimi.ingsw.Controller;

import exceptions.ColumnAlreadyFullException;
import exceptions.OutOfShelfException;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Enumeration.GameState;
import it.polimi.ingsw.Model.Enumeration.Phase;
import it.polimi.ingsw.Network.Message.MsgType;
import it.polimi.ingsw.Network.Message.S2C.UpdateBoardMessage;
import it.polimi.ingsw.Network.Message.S2C.UpdateShelfMessage;
import it.polimi.ingsw.Network.ServerPack.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the game<br>
 * It is used server-side.
 * @author Guido Gonnella
 */
public class GameController implements Runnable{
    //Game and turn states
    private GameState gameState;
    private Phase turnState;

    //Players
    /**
     * List of the players, used also as the player order in the game
     */
    private ArrayList<String> players;
    /**
     * Current player
     */
    private String currPlayer;

    //Game
    private Game game;
    private ArrayList<Tile> selectedTiles;

    //VirtualView
    /**
     * used for sending and receiving Messages from the client
     */
    private VirtualView virtualView;

    //flags & utils
    private boolean endGamePhase;
    private String firstEnder;

    public GameController(VirtualView vv) {
        //creating the game instance
        game = new Game();

        players = new ArrayList<String>();

        gameState = GameState.INIT;

        virtualView = vv;

        firstEnder = new String();
        endGamePhase = false;

        //TODO modificare la virtualview
        //così dovrebbe andare
        /*for(String username: virtualView.getUsernames()) {
            game.addPlayer(username);
            players.add(username);
        }*/


    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            switch (gameState){
                case INIT -> {
                    game.init();    // initializing the game

                    currPlayer = players.get(0); // setting the first player

                    //starting the game phase
                    gameState = GameState.IN_GAME;
                    turnState = Phase.PICK_TILES;
                }
                case IN_GAME -> {
                    inGame();
                }
                case END -> {
                    end();
                }
            }
        }
    }

    private void inGame(){
        switch (turnState){
            case PICK_TILES -> {
                //broadcast the updated board to all players
                virtualView.writeBroadcast(new UpdateBoardMessage(game.getBoard()));

                selectedTiles = new ArrayList<Tile>();
                ArrayList<Coords> tilesCoords = (ArrayList<Coords>) virtualView.read(currPlayer, MsgType.SELECT_TILE_REQUEST);

                //updating in the model the board and the player's shelf
                for(Coords c : tilesCoords)
                    if(game.getBoard().takeTiles(c.x, c.y).isPresent())
                        selectedTiles.add(game.getBoard().takeTiles(c.x, c.y).get());


                //change the turn phase
                turnState = Phase.SELCT_COLUMN;
            }
            case SELCT_COLUMN -> {
                virtualView.write(currPlayer, MsgType.SHELF_UPDATE, game.getPlayerByNick(currPlayer).getShelf());
                int column = (int) virtualView.read(currPlayer, MsgType.SELECT_COL_REQUEST);

                //inserting the tiles in the current player's shelf
                for(Tile t : selectedTiles)
                    try{
                        //todo -> capire per le eccezioni
                        game.getPlayerByNick(currPlayer).getShelf().putTile(t, column);
                    }catch (ColumnAlreadyFullException | OutOfShelfException e){
                        e.printStackTrace();
                    }

                //checking if the current player's shelf is full
                if(game.getPlayerByNick(currPlayer).getShelf().isFull()) {
                    firstEnder = currPlayer;

                    //starts the end phase
                    gameState = GameState.END;
                }

                //checking if the current player has reached a common goad/public objective
                //it can add points to the player if they have passed an objetive
                game.reachPubObj(game.getPlayerByNick(currPlayer));

                //broadcast the updated shelf to all players
                //todo how to do this? we send the shelf to all players or only to the current?
                //virtualView.writeBroadcast(new UpdateShelfMessage(game.getPlayerByNick(currPlayer).getShelf()));

                //sends the shelf to the current player
                //virtualView.write(currPlayer, MsgType.SHELF_UPDATE, game.getPlayerByNick(currPlayer).getShelf());

                //change the turn phase and the pick the next player
                turnState = Phase.PICK_TILES;
                nextPlayer();
            }
        }
    }


    /**
     * Method to add the points for the players and/or calls the inGame() function if not all the players have finished the last turn.<br>
     * It broadcast to the players the points and other stats
     */
    private void end(){
        //the end method calls the inGame until all the players have done the last turn
        //it checks if the current player is the first in the list
        //it means that the last player in the order have finished the turn
        if(!currPlayer.equals(players.get(0))){
            inGame();

            //don't add the points yet
            //the points are added after every player finished the last turn
            return;
        }

        //POINTS CALCULATION

        //extra point for the first to finish
        game.getPlayerByNick(firstEnder).addPoints(1);

        for(String player : players){
            Player p = game.getPlayerByNick(player);

            //adds the points from private objectives and adjacent tiles
            p.countPoints();
        }

        //broadcast the points to the players
        //TODO is there a message for the points?
        //virtualView.writeBroadcast();
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