package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Enumeration.GameState;
import it.polimi.ingsw.Model.Enumeration.Phase;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.VirtualView;

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

    //VirtualView
    /**
     * used for sending and receiving Messages from the client
     */
    private VirtualView virtualView;

    //

    public GameController(VirtualView vv) {
        //creating the game instance
        game = new Game();

        players = new ArrayList<String>();

        gameState = GameState.INIT;

        virtualView = vv;

        //TODO modificare la virtualview
        /*for(String username: virtualView.getMap().getKeys()) {
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

            }
            case SELCT_COLUMN -> {

            }
        }
    }

    private void end(){

    }
}