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
    private boolean gameEnded;

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
        this.gameEnded = false;
    }

    /**
     * Game course
     */
    @Override
    public void run() {
        while(!gameEnded){
            switch (gameState){
                case START -> {/*inizializza game */}
                case PLAYING -> {
                    switch(turnState){
                        // esegue le diverse azioni del turno
                        case START_TURN -> {}
                        case PICK_TILES -> {}
                        case SELECT_COLUMN -> {}
                        case END_TURN -> {}
                    }
                }
                case END -> {/*mostra a tutti i punti, dice il vincitore, chiude la partita, disconnette i client*/}
            }
        }
    }
}
