package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.ServerPack.NewVirtualView;

import java.util.ArrayList;

/**
 * New temporary gameController
 */
public class NewGameController implements Runnable {

    private NewVirtualView view;
    private Game game;
    private ArrayList<String> usernames;
    private NewGameState gameState;
    private NewTurnState turnState;
    private String currentPlayer;

    /**
     * Constructor of the game controller
     * @param view the virtualView
     */
    public NewGameController(NewVirtualView view){
        this.view = view;
        this.usernames = view.getUsernames();
        this.game = new Game();
        this.gameState = NewGameState.START;
        this.turnState = null;
    }

    /**
     * Game course
     */
    @Override
    public void run() {
       /* switch (gameState){
            case START ->
        }*/
    }
}
